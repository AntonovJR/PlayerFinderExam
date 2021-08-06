package com.example.football.service.impl;

import com.example.football.models.dto.PlayerSeedRootDto;
import com.example.football.models.entity.Player;
import com.example.football.repository.PlayerRepository;
import com.example.football.service.PlayerService;
import com.example.football.service.StatService;
import com.example.football.service.TeamService;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    private static final String PLAYERS_FILE_PATH = "src/main/resources/files/xml/players.xml";
    private final PlayerRepository playerRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final TownService townService;
    private final StatService statService;
    private final TeamService teamService;

    public PlayerServiceImpl(PlayerRepository playerRepository, XmlParser xmlParser,
                             ValidationUtil validationUtil, ModelMapper modelMapper, TownService townService,
                             StatService statService, TeamService teamService) {
        this.playerRepository = playerRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.townService = townService;
        this.statService = statService;
        this.teamService = teamService;
    }


    @Override
    public boolean areImported() {
        return this.playerRepository.count()>0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYERS_FILE_PATH));
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();

        xmlParser
                .fromFile(PLAYERS_FILE_PATH, PlayerSeedRootDto.class)
                .getPlayerSeedDtos()
                .stream()
                .filter(playerSeedDto -> {
                    boolean isValid = validationUtil.isValid(playerSeedDto);
                    stringBuilder.append(isValid ?
                            String.format("Successfully imported Player %s %s - %s%n",playerSeedDto.getFirstName(),
                                    playerSeedDto.getLastName(), playerSeedDto.getPosition())
                            : String.format("Invalid Player%n"));

                    return isValid;
                })
                .map(playerSeedDto -> {
                    Player player = modelMapper.map(playerSeedDto, Player.class);
                    player.setStat(statService.getById(playerSeedDto.getStat().getId()));
                    player.setTeam(teamService.getByName(playerSeedDto.getTeam().getName()));
                    player.setTown(townService.getTownByName(playerSeedDto.getTown().getName()));

                    return player;
                })
                .forEach(playerRepository::save);

        return stringBuilder.toString();
    }

    @Override
    public String exportBestPlayers() {
        StringBuilder builder=new StringBuilder();
        List<Player> bestPlayers = playerRepository.getTopPlayers(LocalDate.of(1995, 1,1),
                LocalDate.of(2003,1,1));
        bestPlayers.forEach(player -> {
            builder.append(String.format("""
                    Player - %s %s%n\tPosition - %s
                    \tTeam - %s
                    \tStadium - %s
                    """,player.getFirstName(),player.getLastName(),player.getPosition(),player.getTeam().getName(),player.getTeam().getStadiumName()));

        });

        return builder.toString().trim();
    }
}
