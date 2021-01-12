import java.util.*;

import java.awt.Font; //this import states fonts, which are used to render text in a visible way
import java.awt.Graphics; //abstract base class for all graphical contexts
import java.awt.Graphics2D; //fundamental class for 2D rendering in Java
import java.awt.RenderingHints; //suggestions to Java2D about how it should perform its rendering
import java.awt.image.BufferedImage; //used to handle and manipulate the image data

import java.io.File;  // Import the File class
import java.io.IOException;  //Exception Handling
import java.io.FileNotFoundException;  // Import this class to handle errors


/**DATE: January 12, 2021
 * This is a simple application that allows the user to review for the OCA JAVA SE8 Programmer I Certification Exam
 * This program has two features: first, it lets the user review java concepts before taking the quiz.
 * Second, is it has a multiple choice quiz that prepares the user for the certification exam
 * @author dan honorio
 * @version 1.0
 */


public class Quizlet {

	/**This interface initializes an array of type string
     */
  public interface Question {
    String[] initialize();
  }

  /**This class does the following:
   * it implements the interface Question
   * it also overrides the Question interface
   * returns empty string
   */
  static class basicQuestion implements Question {
    
    @Override
    public String[] initialize() {
      return new String[]{"","",""};
    }
  }
  
  /**This class is like the basis for the decorator.
   *This abstract class provides the format for the other decorators
   */
  abstract static class QuestionDecorator implements Question {
   

	private final Question question;

    /**constructor with @param question as input
     */
    public QuestionDecorator (Question question){
      this.question = question;
    }

    /**overrides / changes the functionality of the basis class which is the question
     * @return the format for other decorators
     */
    @Override
    public String[] initialize() {
      return question.initialize();
    }
  }

  /**This class extends the QuestionDecorator method
   * This class does the following:
   * first, it initializes an array of String
   * it also checks for exceptions - errors
   * its main purpose is it builds a decorator that takes input from files
   */
  public static class QuestionAttributes extends QuestionDecorator {

    protected int number;

    /**
     * constructor with
     * @param question @param number
     * as its input
     */
    public QuestionAttributes (Question question, int number) {
      super(question);
      this.number = number;
    }

    /**Question Initializer Method
     * This method opens and reads file form the text files
     * @return attributeArray
     */
    @Override
    public String[] initialize() {
      String[] attributeArray = {String.format("Question #%d\n",number),"--------------- Choices ---------------\n",""};
      try {
        String fileName = String.format("question%d.txt",number); //String formatting
        File openFile = new File(fileName);
        Scanner readFile = new Scanner(openFile);
        int counter = 0;

        while (readFile.hasNextLine()) { // we want to move to another attribute if it detects a blank line in the text file
          String data = readFile.nextLine(); //reads line
          if (data.isBlank()) { //if blank, counter is incremented
            counter += 1;
            continue;
          }
          /*
           * text file lines normally includes new line
           */
          attributeArray[counter] = attributeArray[counter] + data + "\n";
        }
        readFile.close(); //closes the file output stream
      }
      catch (FileNotFoundException e) { //if reading file is not possible, we go into the catch
        System.out.println("An error has occurred while rendering the text.");
        e.printStackTrace();
      }
      return attributeArray;
    }
  }

  /**Integer Iterator Implementation
   */
  public static Iterator<Integer> range(int start, int end) {
    return new Iterator<>() {
      private int index = start;
      @Override
      public boolean hasNext(){
        return index < end;
      }
      @Override
      public Integer next(){
        if(!hasNext()){
          throw new NoSuchElementException();
        }
        return index ++;
      }
    };
  }

  /**This method is responsible for the general printing from file
   *It takes @param Filename @param Type_ and @param number as parameters
   * If the input is question, then it will print lines from the question file
   * otherwise, if the input is reviewer, then it will print lines from the reviewer file
   */
  public void printFile(String Filename, String Type_, int number) {
    if (Type_.equals("question")){ //for question text file
                                   //prints the choices and questions
      Question question = new QuestionAttributes(new basicQuestion(), number);
      String[] questionString = question.initialize();
      System.out.println(questionString[0]);
      System.out.println(questionString[1]);
    }

    if (Type_.equals("reviewer")){ //for reviewer txt file
      try {
        File openFile = new File(Filename); //open file
        Scanner readFile = new Scanner(openFile); //reads file
        while (readFile.hasNextLine()) { //while it still has next lines, it will implement the following
          String data = readFile.nextLine();
          System.out.println(data);
          if (data.isBlank()) { //if blank, it'll execute the codes below
            System.out.println("==============================\nPress \"ENTER\" to continue...\n==============================\n");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            clrscr();
          }
        }
        readFile.close();
      }
      catch (FileNotFoundException e) {
        System.out.println("An error has occurred while rendering the text.");
        e.printStackTrace();
      }
    }
  }
  /**This method clears the screen in command prompt
   */
  public static void clrscr(){
    try {
      if (System.getProperty("os.name").contains("Windows"))
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      else
        Runtime.getRuntime().exec("clear");
    } catch (IOException | InterruptedException ex) {}
  }

  /**This method is the one that let's the user answer the quiz
   * score parameter stores the score of the user
   * the scanner part, is where the program accepts input from the user
   */
  public void answerQuizlet(){
    int score = 0;
    Scanner userInput1 = new Scanner(System.in); //input
    int[] questionArray = {1,2,3,4,5,6,7,8,9,10}; //question array
    String [] inputArray = {"A","B","C","D"}; //input array

    for (int i = 0; i < 10; i++) { //for loop that randomizes my question array
      Random rand = new Random();
      int randomNum = rand.nextInt(10);
      int temp = questionArray[randomNum];
      questionArray[randomNum] = questionArray[i];
      questionArray[i] = temp;
    }

    /*
     * Integer Iterator Implementation
     * what it does is, it accepts input from the user and if the input is in the input Array,
     * it moves on to the second conditional
     * the second if statement checks if the answer is correct and if it is, it increments the score.
     * it also does this: if the user inputs a choice which is not in the inputArray,
     * it will display invalid input until the user inputs the desired input of this program
     */
    var iterator = range(0, 10);
    while (iterator.hasNext()) {
      clrscr();
      int i = questionArray [iterator.next()];
      Question question = new QuestionAttributes(new basicQuestion(), i);
      String[] questionString = question.initialize();
      String filename = String.format("question%d.txt", i);
      while (true) {
        printFile(filename, "question", i);
        System.out.print("Answer: ");
        String input = userInput1.nextLine();
        System.out.print(input);
        if (Arrays.asList(inputArray).contains(input)) {
          if (input.toCharArray()[0] == questionString[2].toCharArray()[0]){
              score++;
          }
          break;
        }
        clrscr();
        System.out.print("INVALID INPUT - TRY AGAIN\n");
      }
    }

    /*
     * The following block of codes does the following:
     * first, it calls the clrscr method
     * next is it prints the following lines if the @param score satisfies the conditions
     */
    clrscr();
    if (score == 10){
      System.out.println("Excellent! You got: " + score + "/10");
      System.out.println("You are now ready to take the OCA JAVA SE8 Programmer I Certification Exam!");
    }

    else if (score <= 9 && score >=7){
      System.out.println("Great! You got: " + score + "/10");
    }

    else if (score <= 7 && score >=5){
      System.out.println("Good! You got: " + score + "/10");
    }

    else{
      System.out.println("You got: " + score + "/10");
      System.out.println("It seems that you need more practice.");
    }

    System.out.println("Press Enter key to continue...");
    Scanner userInput = new Scanner(System.in);
    userInput.nextLine();

  }

  /**This method does the following:
   *First, it clears the screen on the prompt
   *next is it reads through the lines of our reviewer text file
   */
  public void readReviewers(){
    clrscr();

    Random rand = new Random();
    int randomNum = rand.nextInt(1);
    randomNum ++;
    String filename = String.format("reviewer%d.txt", randomNum);
    printFile(filename, "reviewer", randomNum);

    System.out.println("Press Enter key to continue...");
    Scanner userInput = new Scanner(System.in);
    userInput.nextLine();
  }

  /** Main method
   * calls the clrscr method
   * generates asccii art
   * asks input from the user whether they would like to review first or take the quiz
   */
  public static void main (String [] args){
    while (true) {

      /*
       * calls the clrscr method
       * creates a new object named Quizlet1 of class Quizlet
       */
      clrscr();
      Quizlet Quizlet1 = new Quizlet();

      /*
       * @param width sets the width's dimension for this program's ascii art
       * @param height sets the height's dimension for this program's ascii art
       */
      int width = 150;
      int height = 30;

      /*
       * The blocks of code below does the following:
       * creates an object named image of buffered image class in type rgb
       * gets the graphic context for our ascii art by calling the getGraphics() method
       * sets the graphics font type, font size, and font's typographical emphasis
       */

      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      Graphics g = image.getGraphics();
      g.setFont(new Font("Montserrat", Font.BOLD, 20));

      /*
       * The blocks of code below does the following:
       * Graphics2D g2 is the the block of code which is responsible for the cool 2D stuff for our ascii art.
       * It also transforms our graphics into 2D.
       * RenderingHints attribute is used to specify whether you want objects to be rendered as quickly as possible or
       * whether you prefer that the rendering quality be as high as possible
       * TEXT_ANTIALIASING is responsible in fixing our text quality in our program.
       * I don't want my ascii art to appear pixelated so I used antialiasing.
       */
      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      g2.drawString("JAVA QUIZLET", -2, 20); 

      /*
      *Iterate through the pixels of the image from top to bottom. 
      *Whenever a white pixel is detected, we add the art symbol otherwise we add a space.
      *responsible mainly for generating our ascii art
      */      
      for (int y = 0; y<height; y++){            
        StringBuilder builder = new StringBuilder();

        for (int x=0; x<width; x++){
          builder.append(image.getRGB(x,y) == -16777216 ? " " : "@"); 
        }

        /*
         * prints the ascii art
         */
        System.out.println(builder);
      }

      /*
       * These are the two opening statements of the application
       */
      System.out.println("WELCOME to OCA JAVA SE8 PROGRAMMER I REVIEWER!\n");
      System.out.println("INSTRUCTIONS: Please use CAPITAL LETTERS when answering the quiz.\n");

      /*
       * Accepts input from the user to proceed to the next stage
       */
      System.out.println("Press Enter key to continue...");
      Scanner userInput = new Scanner(System.in);
      userInput.nextLine();

      label:
      while (true) {

        /*
         * The following blocks of code does the following:
         * calls again the clrscr method
         * Displays the Input REVIEWER/QUZ... on the prompt
         * calls the readReviewer method if the input is reviewer or REVIEWER
         * otherwise it will call the answerQuizlet method if the user's input is quiz or QUIZ
         * it also breaks the loop if the user tries to input return or RETURN
         */
        clrscr();
        System.out.println("Input REVIEWER for a short reviewer, QUIZ for a short quiz");
        Scanner userInput1 = new Scanner(System.in);
        String input = userInput1.nextLine();
        switch (input) {
          case "REVIEWER":
          case "reviewer":
            Quizlet1.readReviewers();
            break;
          case "QUIZ":
          case "quiz":
            Quizlet1.answerQuizlet();
            break;
          case "RETURN":
          case "return":
            break label;
        }
      }

    }
  }
}
