public class Main
{
    public static void main(String[] args)
    {

        // Test  -> Gives error due to file values are not correct format

// TEST 1

        String source1 = "/Users/name/Desktop/Java/Assignment5/test/myfile.txt" ;
        String dest1 = "/Users/name/Desktop/Java/Assignment5/test/myfile2.csv" ;
        System.out.println("TEST1: ");
        ConvertFile.convert(source1, dest1);
        ConvertFile.normalize(source1);

// TEST2
        String source2 = "/Users/phillipjohnson/Desktop/Java/Assignment5/test/myfile4.csv" ;

        String dest2 = "/Users/phillipjohnson/Desktop/Java/Assignment5/test/myfile.txt" ;

        System.out.println("TEST2: \n");
        ConvertFile.convert(source2, dest2);

        ConvertFile.normalize(source2);

    } //end of main method

} //end of Main class
