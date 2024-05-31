package com.example.bookstore.api;

import com.example.bookstore.dto.ClobaSummary;
import com.example.bookstore.dto.CommentDto;
import com.example.bookstore.service.ClobaSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ClobaSummaryController {

    @Autowired
    private ClobaSummaryService clobaSummaryService;

    @PostMapping("/api/summary")
    public ClobaSummary getSummary(@RequestBody List<CommentDto> commentDto){
        return clobaSummaryService.clobaSum(commentDto);
    }
}
