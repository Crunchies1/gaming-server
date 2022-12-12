package com.adam.gamingserver.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.adam.gamingserver.model.Game;
import com.adam.gamingserver.services.GameService;

@RestController
@RequestMapping(path="/game") // This means URL's start with /demo (after Application path)
public class GameController {
	@Autowired
    private GameService gameService;
	
	@GetMapping(path="/status")
	public ResponseEntity<String> status () {
		return new ResponseEntity<>("Game service up!", HttpStatus.OK);
	}

    @PostMapping(path="/add") // Map ONLY POST Requests
    public ResponseEntity<Game> addGame (@RequestParam String name) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        Game newGame = new Game(name);
        try {
            Game game = gameService.save(newGame);
			return new ResponseEntity<>(game, HttpStatus.CREATED);
		} catch (Exception e) {
			throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Error in adding game.", e);
		}
    }

    @PostMapping(path="/delete") // Map ONLY POST Requests
    public ResponseEntity<Boolean> deleteGame (@RequestParam long id) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        try {
            Optional<Game> savedGame = gameService.findOne(id);
            if (!savedGame.isPresent()) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Game with ID: " + id + " not found.");
            }
			return new ResponseEntity<>(gameService.delete(savedGame.get()), HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Error in deleting game.", e);
		}
    }

    @GetMapping(path="/get/{id}") // Map ONLY GET Requests
    public ResponseEntity<Game> getGame(@PathVariable("id") long id) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        try {
            Optional<Game> game = gameService.findOne(id);
            if (!game.isPresent()) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Game with ID: " + id + " not found.");
            }
			return new ResponseEntity<>(game.get(), HttpStatus.FOUND);
		} catch (Exception e) {
			throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Error in retrieving game.", e);
		}
    }

    @GetMapping(path="/list")
    public ResponseEntity<Iterable<Game>> findGames() {
		try {
			return new ResponseEntity<>(gameService.findAll(), HttpStatus.FOUND);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in listing games." ,e);
		}
    }

	@PostMapping(path="/play/{id}")
    public ResponseEntity<Float> playGame(@PathVariable("id") long id, @RequestParam float wager) {
		try {
			Optional<Game> game = gameService.findOne(id);
			if (!game.isPresent()) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Game with ID: " + id + " not found.");
            }
			float returns = gameService.playGame(game.get(), wager);
			return new ResponseEntity<>(returns, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in playing game." ,e);
		}
    }
}

