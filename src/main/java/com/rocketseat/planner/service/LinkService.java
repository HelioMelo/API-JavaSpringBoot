package com.rocketseat.planner.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocketseat.planner.domain.link.Link;
import com.rocketseat.planner.domain.trip.Trip;
import com.rocketseat.planner.dto.LinkDTO;
import com.rocketseat.planner.repository.LinkRepository;
import com.rocketseat.planner.response.LinkResponse;

@Service
public class LinkService {

	@Autowired
	private LinkRepository linkRepository;
	
	public LinkResponse registerLink(LinkDTO linkDTO , Trip trip) {
		Link newLink = new Link(linkDTO.title(),linkDTO.url(),trip);
		
		this.linkRepository.save(newLink);
		
		return new LinkResponse(newLink.getId());
	}

	
	 public List<LinkDTO> getAllLinkFromId(UUID tripId) {

	        return linkRepository.findByTripId(tripId).stream()
	            .map(link -> new LinkDTO(
	            		link.getId(),
	            		link.getTitle(),
	            		link.getUrl()
	            ))
	            .collect(Collectors.toList());
	    }
	
}
