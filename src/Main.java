//important imports for program
import java.util.ArrayList;
import java.util.Scanner;

//importing this helps shorten code
import static java.lang.System.out;

public class Main {

    //initialize assorted variables
    static String[] dimensionSplit = {};
    static String[] roverSplit = {};
    private static ArrayList<Character> directionSplit = new ArrayList<Character>();
    static int roverFirstCoordinate = 0;
    static int roverSecondCoordinate = 0;
    static String roverCardinalCoordinate = "";
    static int gridFirstDimension = 0;
    static int gridSecondDimension = 0;

    public static void main(String[] args) {
        //initialize scanner
        Scanner rovers = new Scanner(System.in);
        //Displays introduction to User and prompts for grid size
        printInstructions(1);
        int roverCount = rovers.nextInt();

        printInstructions(2);
        //these try-catches make sure the input given is in the appropriate format
        try{
            //take in grid dimensions
            acceptDimensions();
        } catch(Exception e){
            invalid(1);
        }
        //this loops until the desired amount of rovers entered by user has been reached
        for( int i = 0; i < roverCount; i++){

            printInstructions(3);

            try{
                roverOneInput();
            } catch(Exception e){
                invalid(2);
            }

            printInstructions(4);

            try{
                directionInput();
            } catch(Exception e){
                invalid(3);
            }
            //this takes every character in the string entry given by user for direction, and applies each to the rovers as movement
            for(char movement : directionSplit){
                roverMovement(roverCardinalCoordinate, movement);

                //this ensures that the rover cannot be shown to be out of bounds of the grid given
                preventOutOfBounds(gridFirstDimension, gridSecondDimension, roverFirstCoordinate);
                preventOutOfBounds(gridFirstDimension, gridSecondDimension, roverSecondCoordinate);
            }
            //print out results
            out.println("Your Rover ended up " +
                    "at: " + roverFirstCoordinate + " " + roverSecondCoordinate + " " + roverCardinalCoordinate);
            //clear directions for next rover (if there is one)
            directionSplit.clear();
        }

    }

    //this function takes in the direction the rover is facing and the current desired movement, then applies the changes to the rover's coordinates
    public static void roverMovement(String cardinal, char movement){
        //if the user said to move forward
        if(movement == 'M'){
            switch(cardinal){
                case "N":
                    roverSecondCoordinate += 1;
                    break;
                case "E":
                    roverFirstCoordinate += 1;
                    break;
                case "S":
                    roverSecondCoordinate -= 1;
                    break;
                case "W":
                    roverFirstCoordinate -= 1;
                    break;
            }
        //if the user said to turn left
        } else if(movement == 'L'){
            switch(cardinal){
                case "N":
                    roverCardinalCoordinate = "W";
                    break;
                case "E":
                    roverCardinalCoordinate = "N";
                    break;
                case "S":
                    roverCardinalCoordinate = "E";
                    break;
                case "W":
                    roverCardinalCoordinate = "S";
                    break;
            }
        //if the user said to turn right
        } else if(movement == 'R'){
            switch(cardinal){
                case "N":
                    roverCardinalCoordinate = "E";
                    break;
                case "E":
                    roverCardinalCoordinate = "S";
                    break;
                case "S":
                    roverCardinalCoordinate = "W";
                    break;
                case "W":
                    roverCardinalCoordinate = "N";
                    break;
            }
        }
    }

    //this function checks if the current coordinates of the rover are out of bounds, and if so corrects it
    public static void preventOutOfBounds(int x, int y, int check){
        if (check > x){
            roverFirstCoordinate = x;
        } else if (check > y){
            roverSecondCoordinate = y;
        }

        if (roverFirstCoordinate < 0){
            roverFirstCoordinate = 0;
        }

        if (roverSecondCoordinate < 0){
            roverSecondCoordinate = 0;
        }
    }

    //this function takes in the input of the rover, and will run invalid and repeat itself if the user enters coordinates outside the grid given
    public static void roverOneInput(){
        Scanner rover = new Scanner(System.in);
        String roverInput = rover.nextLine();
        roverSplit = roverInput.split(" ");
        try {
            roverFirstCoordinate = Integer.parseInt(roverSplit[0]);
            roverSecondCoordinate = Integer.parseInt(roverSplit[1]);
            roverCardinalCoordinate = roverSplit[2];
        } catch(Exception e){
            invalid(2);
        }

        if(roverFirstCoordinate > gridFirstDimension ){
            invalid(2);
        }
        if(roverSecondCoordinate > gridSecondDimension){
            invalid(2);
        }
    }

    //this function takes in the input of the directions for the rover entered as a string by the user. it splits each of these directions into an array for later reference
    public static void directionInput(){
        Scanner rover = new Scanner(System.in);
        String directionInput = rover.nextLine();
        for (int i = 0; i < directionInput.length(); i++){
            directionSplit.add(directionInput.charAt(i));
        }
    }

    //this function takes in the input of the dimensions of the grid the rover will drive on. it catches if the dimensions are invalid and repeats itself
    public static void acceptDimensions(){
        Scanner rover = new Scanner(System.in);
        String dimensionInput = rover.nextLine();
        if (dimensionInput.contains(",")){
            dimensionSplit = dimensionInput.split(",");
        } else{
            dimensionSplit = dimensionInput.split(" ");
        }
        try {
            gridFirstDimension = Integer.parseInt(dimensionSplit[0]);
            gridSecondDimension = Integer.parseInt(dimensionSplit[1]);
        } catch(Exception e){
            invalid(1);
        }
    }

    //this function is called when instructions need be printed to the user. it's easier to have it out of the way
    public static void printInstructions(int whichStep) {
        if (whichStep ==1){
            out.println("Hello and welcome to NASA's Mars Rover Control Hub!\n" +
                    "First, how many Rovers are there?");
        } else if (whichStep == 2){
            out.println("Please enter the size of the two-dimensional grid as a comma-separated OR space-separated list:\n" +
                    "(ex:  5,5  <OR>  3 6  <OR>  9 3  <OR>  8,2)");
        } else if (whichStep == 3){
            out.println("Thank you.\nNow please enter the Coordinates of the current Rover and it's cardinal facing direction:\n" +
            "(ex:  1 2 N   <OR>   3 3 E)");        
        } else if(whichStep == 4){
            out.println("Thank you.\nNow please enter the set of directions this Rover will take across the grid:\n" +
            "(ex: LMLMLMLMM   <OR>   MMRMMRMRRM)");
        }
        
    }

    //this function is called whenever the user entered input that was not allowed. it returns the same instructions and function
    // the user made their error on
    public static void invalid(int whichStep){
        out.println("This is an invalid input type.\n");
        if (whichStep == 1){
            printInstructions(2);
            acceptDimensions();
        } else if (whichStep == 2){
            printInstructions(3);
            roverOneInput();
        } else if (whichStep == 3){
            printInstructions(4);
            directionInput();
        }
    }
}
