import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;

public class Specimen {

    static int compareFiles(byte[] bytes0, byte[] bytes1){
        System.out.println("File 1: " + bytes0.length);
        System.out.println("File 2: " + bytes1.length);
        int commonLength = Math.min(bytes0.length, bytes1.length);

        int changeCount = 0;
        for(int i = 0; i < commonLength; i++){
        if(bytes0[i] != bytes1[i]){
            changeCount++;
                System.out.println("offset " + 
                String.format("%08x: ", i) + 
                String.format("%02x", bytes0[i])  + " -> " + 
                String.format("%02x", bytes1[i])
                );
            }
        }
            
        if(changeCount == 0 && bytes0.length == bytes1.length){
            System.out.println("Files are identical");
        } else if (changeCount > 0){
            System.out.println(changeCount + " bytes modified");
                }

        if(bytes0.length > bytes1.length){
            int difference = bytes0.length - bytes1.length;
            System.out.println(difference + " trailing byte removed.");
        } else if(bytes1.length > bytes0.length) {
            int difference = bytes1.length - bytes0.length;
            System.out.println(difference + " trailing byte added.");
                }
        return changeCount;
    }

    static void printHexDump(byte[] bytes){
            if(bytes.length == 0){
                System.out.println("File is empty (0 bytes)");
                return;
            }

            int displayLength = Math.min(bytes.length, 128);
            StringBuilder ascii = new StringBuilder(); // collects all the characters to display at end of the row
            for(int i = 0; i < displayLength; i++){ // run through till reached the last character
                if(i % 16 == 0){
                    System.out.print(String.format("%08x: ", i)); // if first line, shows file offset
                }
                char character = (char) bytes[i]; // used to add the characters that are doing to be appended at the end of line
                System.out.print(String.format("%02x", bytes[i])  + " " ); // shows the memory bytes
                if(bytes[i] < 32 || bytes[i] > 126){ // used to make sure non printable characters don't mess up the rows
                    ascii.append('.');
                } else{
                    ascii.append(character);
                }

                if((i+1) % 16 == 0 || i == displayLength - 1){ // checks if a row has 16 bytes or reached last byte
                    if(i == displayLength - 1 && displayLength % 16 != 0){ // checks if last byte and also row not filled
                        int bytesLastRow = displayLength % 16; // counts how many bytes in the last row
                        int emptyPositions = 16 - bytesLastRow; // how many empty positions in last row
                        for(int r = 0; r < emptyPositions;r++){ // fill outs empty positions in last row for the characters can be printed allgined
                            System.out.print("   "); 
                        }
                    }
                    System.out.print(ascii); // prints the appended charaters at end
                    ascii.setLength(0); // resets string builder for next row
                    System.out.println(); // line break to start new row
                    
                }

                
            }

            if(bytes.length > displayLength){
                    System.out.print(bytes.length - displayLength + " additional bytes not displayed.");
                }
            
    }

    static String detectFileType(byte[] bytes){
            if(bytes.length >= 4){
                if(
                (bytes[0] & 0xFF) == 0xCA && 
                (bytes[1] & 0xFF) == 0xFE && 
                (bytes[2] & 0xFF) == 0xBA && 
                (bytes[3] & 0xFF) == 0xBE 
                ){ 
                    return "Java Class";
                }
            }

            if(bytes.length >= 8){
                if(
                (bytes[0] & 0xFF) == 0x89 && 
                (bytes[1] & 0xFF) == 0x50 && 
                (bytes[2] & 0xFF) == 0x4E && 
                (bytes[3] & 0xFF) == 0x47 && 
                (bytes[4] & 0xFF) == 0x0D && 
                (bytes[5] & 0xFF) == 0x0A && 
                (bytes[6] & 0xFF) == 0x1A && 
                (bytes[7] & 0xFF) == 0x0A
                ){ 
                    return "PNG Image";
                }
            }

            if(bytes.length >= 4){
                if(
                (bytes[0] & 0xFF) == 0x50 && 
                (bytes[1] & 0xFF) == 0x4B && 
                (bytes[2] & 0xFF) == 0x03 && 
                (bytes[3] & 0xFF) == 0x04 
                ){ 
                    return "Zip Archive";
                }
            }
            return "Other";
    }
    
    public static void main(String[] args) {
        if(args.length == 0){ // checks if an arugement is given to check
            System.out.println("Usage: java Specimen <filename>");
            return;
        }     

        try {

            if(args.length ==2){
                byte[] bytes0 = Files.readAllBytes(Path.of(args[0]));
                byte[] bytes1 = Files.readAllBytes(Path.of(args[1]));
                compareFiles(bytes0, bytes1);
                return ;
            }

            byte[] bytes = Files.readAllBytes(Path.of(args[0])); // takes raw bytes from the file
            if (bytes.length == 0) {
                System.out.println("File is empty (0 bytes)");
                    return;
            }
            String type = detectFileType(bytes);
            System.out.println("File type: " + type);
            printHexDump(bytes);
            
            } catch (IOException e){
            System.out.println("could not open " + args[0]);
                }
    }
}