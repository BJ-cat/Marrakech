# Test plan

## List of classes

* List below all classes in your implementation that should have unit tests.
* For each class, list methods that can be tested in isolation.
* For each class, if there are conditions on the class' behaviour that cannot
  be tested by calling one method in isolation, give at least one example of
  a test for such a condition.

Do **not** include in your test plan the `Marrakech` class or the predefined
static methods for which we have already provided unit tests.


#Assam
- testAssamConstructor()
- testAssamMove()
- testAssamRotate()

#Player
- testPlayerState()
- testPlayerPayment()
- testPlayerString()

#Board
- testBoardSize()

#Rug
- testRugColor()
- testRugId()
- testRugCoordinate()
- testRugImplementValid()
- - add(AssamPosition); size+1; 
    add(ExistRugPosition); size+1

#Tile
- testTileConstructor()
- TestIsRugOnTile()
- testTileColor()
- testTileId()
- testTileCoordinate()


