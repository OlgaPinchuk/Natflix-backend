package com.novare.natflix.models.content;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="series")
public class Series extends Content{

    @OneToMany(mappedBy = "series", cascade = CascadeType.REMOVE)
    Set<Episode> episodes = new HashSet<>();

    public Series() {}

    public Set<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Set<Episode> episodes) {
        this.episodes = episodes;
    }

    public void addEpisode(Episode episode) {
        episodes.add(episode);
        episode.setSeries(this);
    }
}


