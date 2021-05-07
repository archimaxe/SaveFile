/*
+ Создать три экземпляра класса GameProgress.
Сохранить сериализованные объекты GameProgress в папку savegames из предыдущей задачи.
Созданные файлы сохранений из папки savegames запаковать в архив zip.
Удалить файлы сохранений, лежащие вне архива.
 */

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) throws Exception{
        GameProgress checkPoint_1 = new GameProgress(1, 1,1, 1.0);
        GameProgress checkPoint_2 = new GameProgress(2, 2,2, 2.0);
        GameProgress checkPoint_3 = new GameProgress(3, 3,3, 3.0);
        GameProgress noCheckPoint = new GameProgress(1, 2,3, 4.0);

        saveGame("C:\\Users\\razel\\Desktop\\Новая папка\\Setup\\Games\\savegames\\check1", checkPoint_1);
        saveGame("C:\\Users\\razel\\Desktop\\Новая папка\\Setup\\Games\\savegames\\check2", checkPoint_2);
        saveGame("C:\\Users\\razel\\Desktop\\Новая папка\\Setup\\Games\\savegames\\check3", checkPoint_3);
        saveGame("C:\\Users\\razel\\Desktop\\Новая папка\\Setup\\Games\\savegames\\noCheckPoint", noCheckPoint);

        List<String> pathZipList = new ArrayList<>();
        pathZipList.add("C:\\Users\\razel\\Desktop\\Новая папка\\Setup\\Games\\savegames\\check1");
        pathZipList.add("C:\\Users\\razel\\Desktop\\Новая папка\\Setup\\Games\\savegames\\check2");
        pathZipList.add("C:\\Users\\razel\\Desktop\\Новая папка\\Setup\\Games\\savegames\\check3");

        zipFiles("C:\\Users\\razel\\Desktop\\Новая папка\\Setup\\Games\\savegames\\zip.zip", pathZipList);
    }

    public static void saveGame(String savePath, GameProgress gameProgress) throws Exception{
        try (FileOutputStream fos = new FileOutputStream(savePath, false);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        }
    }

    public static void zipFiles(String zipPath, List<String> list ) throws FileNotFoundException, IOException {
        for (int i = 0; i < list.size(); i++) {
            try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath));
            FileInputStream fis = new FileInputStream(list.get(i))){
                ZipEntry entry = new ZipEntry(list.get(i));
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
            }
        }
    }
}
