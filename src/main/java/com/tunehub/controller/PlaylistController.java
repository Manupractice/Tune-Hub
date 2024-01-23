package com.tunehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tunehub.entities.Playlist;
import com.tunehub.entities.Song;
import com.tunehub.services.PlaylistService;
import com.tunehub.services.SongService;

@Controller
public class PlaylistController {
	@Autowired
	SongService songService;
	@Autowired
	PlaylistService playlistService;

	@GetMapping("createPlaylist")
	public String createPlaylist(Model model) {
		List<Song> songList = songService.fetchAllSongs();
		model.addAttribute("songs", songList);
		return "createPlaylist";
	}

	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		playlistService.addPlaylist(playlist);

		List<Song> songList = playlist.getSongs();
		for (Song s : songList) {
			s.getPlaylists().add(playlist);
			songService.updateSong(s);
		}
		return "adminHome";
	}
	@GetMapping("/viewAllPlaylist")
	public String viewAllPlaylist(Model model) {
		List<Playlist> allPlaylist=playlistService.fetchAllPlaylist();
		model.addAttribute("allPlaylist", allPlaylist);
		return "displayPlaylist";
	}

}
