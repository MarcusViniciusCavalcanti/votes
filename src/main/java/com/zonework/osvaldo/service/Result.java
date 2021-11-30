package com.zonework.osvaldo.service;

import com.zonework.osvaldo.model.Application;
import com.zonework.osvaldo.model.Place;
import com.zonework.osvaldo.model.Resultado;
import com.zonework.osvaldo.model.Votes;
import com.zonework.osvaldo.repository.PlaceRepository;
import com.zonework.osvaldo.repository.VoteRepository;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Result {

    private final VoteRepository voteRepository;
    private final PlaceRepository placeRepository;
    private final SimpMessagingTemplate simpleMessage;

    public Boolean application() {
        return voteRepository.findById(1L)
            .map(Application::getValue)
            .orElse(Boolean.FALSE);
    }

    public void includeVoteTruncoso() {
        var vote = new Votes();
        placeRepository.findById(1L).ifPresent(place -> {
            place.addVote(vote);
            placeRepository.saveAndFlush(place);
        });

    }

    public void includeVoteBonito() {
        var vote = new Votes();
        placeRepository.findById(2L).ifPresent(place -> {
            place.addVote(vote);
            placeRepository.saveAndFlush(place);
        });

    }

    public void includeVoteGramado() {
        var vote = new Votes();
        placeRepository.findById(3L).ifPresent(place -> {
            place.addVote(vote);
            placeRepository.saveAndFlush(place);
        });

    }

    public void init() {
        var app = new Application();
        app.setId(1L);
        app.setValue(Boolean.TRUE);
        voteRepository.saveAndFlush(app);

    }

    public void stop() {
        voteRepository.findById(1L).ifPresent(app -> {
            app.setValue(Boolean.FALSE);
            voteRepository.saveAndFlush(app);
        });
    }

    public void result() {
        var truncoso = placeRepository.findById(1L)
            .stream()
            .map(place -> new Resultado(place.getName(), place.getDescription(), place.getImage(), place.getVotesList().size()))
            .findFirst()
            .orElse(new Resultado());

        var bonito = placeRepository.findById(2L).stream()
            .map(place -> new Resultado(place.getName(), place.getDescription(), place.getImage(), place.getVotesList().size()))
            .findFirst()
            .orElse(new Resultado());
        var gramado = placeRepository.findById(3L).stream()
            .map(place -> new Resultado(place.getName(), place.getDescription(), place.getImage(), place.getVotesList().size()))
            .findFirst()
            .orElse(new Resultado());

        var resultado = Stream.of(truncoso, gramado, bonito)
            .max(Comparator.comparingInt(Resultado::getAmountVotes))
            .orElse(new Resultado());

        simpleMessage.convertAndSend("/topic/stations", resultado);
    }

    public void setPlaces() {
        var truncoso = new Place();
        truncoso.setId(1L);
        truncoso.setName("Trancoso - BA");
        truncoso.setDescription("Boa escolha Osvaldo desfrutará de praias paradisíacas, bons restaurantes, "
                                + "bares, e claro uma noitada daquelas. Neste cantinho lindo do sul da Bahia.");
        truncoso.setImage("images/truncoso-resultado.jpg");

        var bonito = new Place();
        bonito.setId(2L);
        bonito.setName("Bonito - MT");
        bonito.setImage("images/bonito-resultado.jpg");
        bonito.setDescription("Uau!, Osvaldo descansaram ao som de pássaros, em uma linda e exuberante floresta,"
                                + " terá o privilegio de desfrutar da mãe natureza, além claro de um pouco "
                                + "de adrenalina com os esportes radicais.");

        var gramado = new Place();
        gramado.setId(3L);
        gramado.setName("Gramado - RS");
        gramado.setImage("image/gramado-resultado");
        gramado.setDescription("Super Legal! vocês escolherem o aconchego desta incrível cidade, Gramado foi uma boa escolh, pois"
                               + "é uma excelente lugar para ter momentos maravilhosos, neste pequeno paraíso ecológico.");

        placeRepository.saveAllAndFlush(List.of(truncoso, bonito, gramado));
    }

}
