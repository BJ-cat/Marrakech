Reviewed by: Arnav Kaher (u7228481)

Reviewing code written by: Bei Jin (u7634090)

Component: Board Class

### Comments
The Java code in the 'Board' class provides a framework for managing
a game board. It defines the board's dimensions and provides methods
to place and retrieve 'Rug' on the game board. The class has clear
and easy-to-understand methods that have been implemented logically with
comments, making the code user-friendly and readable.
Furthermore, variables and methods have been appropriately named
which also adds to the readability of the code. However, there are
few things that can be added to the code to enhance documentation.
Firstly, it would be beneficial to add Javadoc-style comments on top
of class methods explaining their purpose and usage, including
their parameters and return values so the user can understand how this
class's code contributes to the overall mechanics of the game.
Bound checking is another area that can be improved in the code. While,
there is a comment in the 'placeRug' method stating, "You'll need to implement
bounds checking," suggesting a bound-checking method will be implemented in
this method, which is great for preventing index out-of-bounds errors; however,
there should be a similar comment in the getRug Method as well to
prevent a similar error.

Might I suggest, the method for checking bounds could look like:

private boolean isValidBound(int x, int y) {
return x >= 0 && x < 7 && y >= 0 && y < 7;
}



