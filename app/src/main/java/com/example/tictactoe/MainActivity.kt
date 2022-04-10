package com.example.tictactoe

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var playerOneTurn = true
    private var playerOneMoves = mutableListOf<Int>()
    private var playerTwoMoves = mutableListOf<Int>()

    private val possibleWins : Array<List<Int>> = arrayOf(
        //horizontal liness
        listOf(1,2,3),  //we are creating buttons dynamically as our board
        listOf(4,5,6),  //these numbers are basically ids of those buttons
        listOf(7,8,9),
        //vertical
        listOf(1,4,7),
        listOf(2,5,8),
        listOf(3,6,9),

        //diagonal
        listOf(1,5,9),
        listOf(3,5,7)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBoard()
    }

    private fun setupBoard() {
         var counter = 1

        val params1 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            0
        )

        val params2 = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT,
        )

        for (i in 1..3){ //for 3 horizontal lines in our parent Linear Layout (board)
            val linearLayout = LinearLayout(this)

            linearLayout.layoutParams = params1
            linearLayout.orientation = LinearLayout.HORIZONTAL
            params1.weight = 1.0F

            for (j in 1..3){ //for 3 buttons in each linearLayout
                val button = Button(this)
                button.id = counter

                button.textSize = 42.0F
                button.layoutParams = params2
                button.setTextColor(ContextCompat.getColor(this, R.color.accent))
                params2.weight = 1.0F

                button.setOnClickListener{
                    recordMove(it)
                }
                counter++
                linearLayout.addView(button)
            }
            board.addView(linearLayout)
        }
    }

    private fun recordMove(view : View?) {
        val buttons = view as Button //casting
        val id = buttons.id

        if (playerOneTurn){
            playerOneMoves.add(id)

            buttons.text = "0"
            buttons.isEnabled = false // once player has click the button he cant click it again

            if (checkWin(playerOneMoves))
                showWinMessage(player_one)
            else{
                playerOneTurn = false
                toggelPlayerTurn(player_two_label, player_one_label)
            }
        }else{
            playerTwoMoves.add(id)

            buttons.text = "X"
            buttons.isEnabled = false // once player has click the button he cant click it again

            if (checkWin(playerTwoMoves))
                showWinMessage(player_two)
            else{
                playerOneTurn = true
                toggelPlayerTurn(player_one_label, player_two_label)
            }
        }
    }

    private fun toggelPlayerTurn(playerOn: TextView, playerOff: TextView) {
        playerOff.setTextColor(ContextCompat.getColor(this, R.color.primary))
        playerOff.setTypeface(null, Typeface.NORMAL)

        playerOn.setTextColor(ContextCompat.getColor(this,R.color.teal_700))
        playerOn.setTypeface(null, Typeface.BOLD)
    }

    private fun checkWin(moves: MutableList<Int>): Boolean {
        var won = false
        if(moves.size >= 3){
            run loop@{
                possibleWins.forEach {
                    if (moves.containsAll(it)){
                        won = true
                        return@loop
                    }
                }
            }
        }
        return won
    }


    private fun showWinMessage(playerName : EditText) {
        var name = playerName.text.toString()

        if (name.isBlank())
            Toast.makeText(this, "Congratulations.!! PLAYER 1 win", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(this, "Congratulations.!! $name win", Toast.LENGTH_LONG).show()
    }

}