# bamboogame

  This is the repository for Project 2-1 of group 12.
  
  In this project we implemented different types of AI representing the opponent of
  This board game, following the simple set of [rules](http://www.marksteeregames.com/Bamboo_rules.pdf) 
  making up this game.
  
## Types of AI
### Minimax
You can find more about this method [here](https://en.wikipedia.org/wiki/Minimax).

### Reinforcement Learning
Using [DL4J](https://deeplearning4j.konduit.ai/)'s Deep Q-learning implementation, to predict an optimal action.

### Hybrid
Combining minimax and Deep Q-learning by training the Network to produce values for the leaf nodes of the Minimax tree.

## setup
  * first build the project by running `.\gradlew build`
  * run the game using `.\gradlew run`
