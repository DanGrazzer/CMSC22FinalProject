# CMSC22FinalProject
Files for CMSC 22 Final Project

=================================

INFO
AUTHOR: Danny D. Honorio, Jr.

MY HOBBY: Playing quiz games

PROGRAM NAME: JAVA QUIZLET

BRIEF DESCRIPTION: This is a simple application that allows the user to review for the OCA JAVA SE8 Programmer I Certification Exam
                   This program has two features: first, it lets the user review java concepts before taking the quiz.
                   Second, is it has a multiple choice quiz that prepares the user for the certification exam

=================================

DESIGN PATTERNS
CREATIONAL PATTERN: SINGLETON -The program only created or allowed one instance of a class.

STRUCTURAL PATTERN: DECORATOR - In my program, I applied the decorator design patten in Question interface. I then added functionalities to my Question interface using 
                                basicQuestion and QuestionDecorator (classes which implements the said interface) and QuestionAttributes(extends QuestionDecorator).
                                To simply put it, the Question interface is like my Christmas Tree. I then decorated it using basicQuestion, QuestionDecorator, and
                                Question Attributes class.
                           
BEHAVIORAL PATTERN: ITERATOR -The program used an Iterator Design pattern by organizing the questions, options, and answers in an array.

=================================

SOFTWARE DEVELOPMENT PROCESS
The program was developed with both TDD (test -> fail -> code -> test -> pass -> refactor) and Incremental Development (write code -> run with test variables -> then writemore code). 

=================================

BEST PRACTICES

1) USE OF NAMING CONVENTIONS
    *explanatory- my methods and class names reveal their intention and purpose. So if another programmer were to edit my or an audience wwere to look at it, it'll be easy for 
                  them to understand
    *has meaningful distinctions- my variables had meaningful distinctions (i did not use a, a1, a2 as my variables name)
    *I applied the PascalNaming Convention for classes and camelNamingConvention for methods.
    *my classs names and methods were easy to pronounce
    
2) ORDERING CLASS MEMBERS BY SCOPE
    *I organizes them from most restrictice(private) to less restrictive(public) and I separated them by blank line
    
3) Documentations
    *I wrote comments in my program



