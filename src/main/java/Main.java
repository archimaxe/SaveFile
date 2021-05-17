/*
+ Создать три экземпляра класса GameProgress.
Сохранить сериализованные объекты GameProgress в папку savegames из предыдущей задачи.
Созданные файлы сохранений из папки savegames запаковать в архив zip.
Удалить файлы сохранений, лежащие вне архива.
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) throws Exception{
        GameProgress checkPoint_1 = new GameProgress(1, 1,1, 1.0);
        GameProgress checkPoint_2 = new GameProgress(2, 2,2, 2.0);
        GameProgress checkPoint_3 = new GameProgress(3, 3,3, 3.0);
        GameProgress noCheckPoint = new GameProgress(1, 2,3, 4.0);

        String basicPath = "C:\\Users\\razel\\Desktop\\Новая папка\\Setup\\Games\\savegames";

        saveGame(basicPath + "\\check1.dat", checkPoint_1);
        saveGame(basicPath + "\\check2.dat", checkPoint_2);
        saveGame(basicPath + "\\check3.dat", checkPoint_3);
        saveGame(basicPath + "\\noCheckPoint.dat", noCheckPoint);

        List<String> pathZipList = new ArrayList<>();
        pathZipList.add(basicPath + "\\check1.dat");
        pathZipList.add(basicPath + "\\check2.dat");
        pathZipList.add(basicPath + "\\check3.dat");

        zipFiles(basicPath + "\\zip.zip", pathZipList, basicPath);

        List<File> filesInFolder = Files.walk(Paths.get(basicPath))
                .filter(Files::isRegularFile)
                .filter(s -> !s.endsWith("zip"))
                .map(Path::toFile)
                .collect(Collectors.toList());
        for (int i = 0; i < filesInFolder.size(); i++) {
            filesInFolder.get(i).delete();
        }
    }


    public static void saveGame(String savePath, GameProgress gameProgress) throws Exception{
        try (FileOutputStream fos = new FileOutputStream(savePath, false);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        }
    }
/*
Для создания архива используется класс ZipOutputStream
Для считывания данных из файла предназначен класс FileInputStream
 */
    public static void zipFiles(String zipPath, List<String> zipPackList, String basicPath) throws FileNotFoundException, IOException {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (int i = 0; i < zipPackList.size(); i++) {
                FileInputStream fis = new FileInputStream(zipPackList.get(i));
                    ZipEntry entry = new ZipEntry(zipPackList.get(i));
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
