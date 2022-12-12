package com.adam.gamingserver.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ElementCollection;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "games")
public class Game {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private @Getter long id;
	
	private @Getter @Setter String name;
	private @Getter boolean canPlay;
    private @Getter @Setter float minWager;

	@ElementCollection
	private Map<Float, Float> returnData;

	@ElementCollection
    private Set<String> specialFeatures;

	public Game() {}

	public Game(String name) {
		this.name = name;
		this.canPlay = false;
		this.minWager = 0;
		this.returnData = new HashMap<Float, Float>();
		this.specialFeatures = new HashSet<String>();
	}

	public Game(long id, String name) {
		this.id = id;
		this.name = name;
		this.canPlay = false;
		this.minWager = 0;
		this.returnData = new HashMap<Float, Float>();
		this.specialFeatures = new HashSet<String>();
	}


	public boolean toggleCanPlay() {
		Set<Float> percentages = returnData.keySet();
		float totalPercent = 0;
		for (Float winPercent : percentages) totalPercent += winPercent;
		if (totalPercent != 100) {
			return false;
		}

		return !this.canPlay;
	}

	public float playGame(float wager) {
		// Make sure game is playable
		if (!canPlay) {
			return 0;
		}

		// Generate random number to see what the outcome of the game is
		Random rand = new Random();
		float winValue = rand.nextFloat();
		
		// See if you have won anything
		float accumulator = 0;
		float returns = 0;
		for (Map.Entry<Float, Float> returnEntry : this.returnData.entrySet()) {
			accumulator += returnEntry.getKey();
			if (winValue <= accumulator) {
				returns = wager * returnEntry.getValue();
				break;
			}
		}

		return returns;
	}
}
