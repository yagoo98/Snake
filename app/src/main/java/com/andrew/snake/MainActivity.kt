package com.andrew.snake

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.andrew.snake.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val viewModel = ViewModelProvider(this).get(SnakeViewModel::class.java)
        viewModel.body.observe(this, Observer {
            binding.content.gameView.snakeBody = it
            binding.content.gameView.invalidate()
        })
        viewModel.score.observe(this, Observer {
            binding.content.score.text = it.toString()
        })
        viewModel.gameState.observe(this, Observer {
            if (it == GameState.GAME_OVER) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Game")
                    .setMessage("Game Over")
                    .show()
            }
        })
        viewModel.apple.observe(this, Observer {
            binding.content.gameView.apple = it
            binding.content.gameView.invalidate()
        })
        viewModel.start()
        binding.content.buttonUp.setOnClickListener { viewModel.move(Direction.UP) }
        binding.content.buttonDown.setOnClickListener { viewModel.move(Direction.DOWN) }
        binding.content.buttonLeft.setOnClickListener { viewModel.move(Direction.LEFT) }
        binding.content.buttonRight.setOnClickListener { viewModel.move(Direction.RIGHT) }

        binding.content.reset.setOnClickListener{viewModel.reset()}
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}