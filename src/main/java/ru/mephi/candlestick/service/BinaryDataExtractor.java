package ru.mephi.candlestick.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.mephi.candlestick.service.dto.Candle;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class BinaryDataExtractor implements Extractor {

    @SneakyThrows
    public List<Candle> getCandlesFromBinary(String fileName, Integer blockSizeInBytes, Integer bitness) {
        if (bitness % 2 != 0) {
            throw new IllegalStateException("Bitness must be divided by 2");
        }

        long bytes = Files.size(Paths.get("/Users/elizaveta/Downloads/" + fileName + ".bin"));

        int sizeOfIntArray = (int) bytes / 4;
        int[] intArray = new int[sizeOfIntArray + 1];
        DataInputStream in = new DataInputStream(new FileInputStream("/Users/elizaveta/Downloads/" + fileName + ".bin"));


        int j = 0;
        int intCounter = 0;

        List<Candle> candles = new ArrayList<>();
        Float leftBorder = 0.0f;
        int read;
        byte[] b = new byte[blockSizeInBytes];
        List<Long> inputList = new ArrayList<>();

        try {
            while ((read = in.read(b)) > 0) {
                //тут уже правильно берется по 2 разряда из исходного файла и транслируется в двоичную систему (по сути частный случай 8 разрядов)
                for (int i = 0; i < b.length; i++) {
//                    System.out.print(b[i] + "  ");
                    long a = Long.parseLong(Integer.toBinaryString((b[i] & 0xFF) + 0x100).substring(1)
//                            + Integer.toBinaryString((b[i + 1] & 0xFF) +
//                            0x100).substring(1)
//                            + Integer.toBinaryString((b[i + 2] & 0xFF) + 0x100).substring(1)

//                            + Integer.toBinaryString((b[i + 3] & 0xFF) + 0x100).substring(1)
                            , 2);
//                            + Integer.parseInt(Integer.toBinaryString((b[i + 1] & 0xFF) + 0x100).substring(1), 2)
//                            + Integer.parseInt(Integer.toBinaryString((b[i + 2] & 0xFF) + 0x100).substring(1), 2) ;
//                    String test = String.format("%8s", Integer.toBinaryString(b[i] & 0xFF)).replace(' ', '0')
//                             + String.format("%8s", Integer.toBinaryString(b[i + 1] & 0xFF)).replace(' ', '0') ;
//                    System.out.print(test);
//                    int a = Integer.parseInt(test, 2);
                    inputList.add(a);
//                    System.out.println(" " + a);
                }

                Candle candle = convertToCandleWithoutSign(inputList, leftBorder);
                candles.add(candle);
                leftBorder = candle.getRightBorder();
//                break;
            }
        } catch (EOFException ignored) {
        }
        in.close();
//        System.out.println("New int " + intArray);
        Path path = Paths.get("/Users/elizaveta/Downloads/" + fileName + ".bin");
        byte[] fileContents = Files.readAllBytes(path);
        int length = fileContents.length;
        int counter = 0;
        System.out.println("Candles " + candles);
        System.out.println(candles.size());
        return candles;
    }

    //    private Candle convertToCandleWithoutSign(byte[] chunk, Float leftBorder){
    private Candle convertToCandleWithoutSign(List<Long> chunk, Float leftBorder) {
        long[] array = new long[chunk.size()];
        for (int i = 0; i < chunk.size(); i++) {
            array[i] = chunk.get(i);
        }
        Candle candle = new Candle();
        candle.setLeftBorder(leftBorder);
        Float sum = 0.0f;
        long min = array[0];
        long max = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
            if (array[i] > max) {
                max = array[i];
            }
            sum += array[i];
        }
        candle.setRightBorder(sum / array.length);
        candle.setHighLine(max);
        candle.setLowLine(min);

        return candle;
    }
}
