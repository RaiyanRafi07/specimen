import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SpecimenTest {

    @Test
    void detectsJavaClass() {
        byte[] signature = {
            (byte) 0xCA, (byte) 0xFE,
            (byte) 0xBA, (byte) 0xBE
        };

        String actual = Specimen.detectFileType(signature);

        assertEquals("Java Class", actual);
    }

    @Test
    void detectsPNG() {
        byte[] signature = {
            (byte) 0x89, (byte) 0x50,
            (byte) 0x4E, (byte) 0x47,
            (byte) 0x0D, (byte) 0x0A,
            (byte) 0x1A, (byte) 0x0A
        };

        String actual = Specimen.detectFileType(signature);
        assertEquals("PNG Image", actual);
    }


    @Test
    void detectsZIP() {
        byte[] signature = {
            (byte) 0x50, (byte) 0x4B,
            (byte) 0x03, (byte) 0x04
        };

        String actual = Specimen.detectFileType(signature);

        assertEquals("Zip Archive", actual);
    }

    @Test
    void detectsUnknownFile() {
        byte[] signature = {
            (byte) 0x41, (byte) 0x42,
            (byte) 0x43, (byte) 0x44
        };

        String actual = Specimen.detectFileType(signature);

        assertEquals("Other", actual);
    }

    @Test
    void rejectsIncompletePNG() {
        byte[] signature = {
            (byte) 0x89, (byte) 0x50,
            (byte) 0x4E, (byte) 0x47
        };;

        String actual = Specimen.detectFileType(signature);

        assertEquals("Other", actual);
    }

    @Test
    void detectsEmptySignature() {
        byte[] signature = {
        };;

        String actual = Specimen.detectFileType(signature);

        assertEquals("Other", actual);
    }

    @Test
    void countsModifiedBytes() {
    byte[] before = {65, 66, 67}; // ABC
    byte[] after = {65, 66, 68};  // ABD

    int actual = Specimen.compareFiles(before, after);

    assertEquals(1, actual);
    }

    @Test
    void countsMultipleChanges() {
    byte[] before = {65, 66, 67, 68}; // ABCD
    byte[] after  = {65, 88, 67, 89}; // AXCY

    int actual = Specimen.compareFiles(before, after);

    assertEquals(2, actual);
    }

    @Test
    void countsIdentticalFiles() {
    byte[] before = {65, 66, 67}; // ABCD
    byte[] after  = {65, 66, 67}; // AXCY

    int actual = Specimen.compareFiles(before, after);

    assertEquals(0, actual);
    }

    @Test
    void compareDifferentLengths() {
    byte[] before = {65, 66, 67}; // ABCD
    byte[] after  = {65, 88};     // AX

    int actual = Specimen.compareFiles(before, after);

    assertEquals(1, actual);
    }

    @Test
    void compareEmptyFiles() {
    byte[] before = {};
    byte[] after  = {};

    int actual = Specimen.compareFiles(before, after);

    assertEquals(0, actual);
    }
}
