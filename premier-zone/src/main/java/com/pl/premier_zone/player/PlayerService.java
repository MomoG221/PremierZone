package com.pl.premier_zone.player;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    public List<Player> getPlayersFromTeam(String teamName){
        return playerRepository.findAll().stream()
                .filter(player -> teamName.equals(player.getTeam()))
                .collect(Collectors.toList()) ;
    }

    public List<Player> getPlayersByName(String searchText){
        return playerRepository.findAll().stream()
                .filter(player -> player.getName().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList()) ;
    }

    public List<Player> getPlayerByPos(String searchText){
        return playerRepository.findAll().stream()
                .filter(player -> player.getPos().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList()) ;
    }

    public List<Player> getPlayerByNation(String searchText){
        return playerRepository.findAll().stream()
                .filter(player -> player.getNation().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList()) ;
    }

    public Player getPlayersByTeamAndPosition(String teamName, String position) {
        return playerRepository.findAll().stream()
                .filter(player -> teamName.equals(player.getTeam()) && position.equals(player.getPos()))
                .findFirst()
                .orElse(null);
    }

    public Player addPlayer(Player player){
        return playerRepository.save(player);
    }

    public Player updatePlayer(Player player){
        Optional<Player> existingPlayer = playerRepository.findByName(player.getName());

        if (existingPlayer.isPresent()){
            Player updatedPlayer = existingPlayer.get();
            updatedPlayer.setName(player.getName());
            updatedPlayer.setNation(player.getNation());
            updatedPlayer.setPos(player.getPos());
            updatedPlayer.setTeam(player.getTeam());

            playerRepository.save(updatedPlayer) ;
          
        } 
         
        return null;

    }

    @Transactional
    public void deletePlayer(String playerName){
        playerRepository.deleteByName(playerName);
    }

}
