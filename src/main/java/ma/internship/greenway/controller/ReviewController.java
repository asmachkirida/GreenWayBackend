package ma.internship.greenway.controller;

import ma.internship.greenway.dto.ReviewDTO;
import ma.internship.greenway.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewDTO reviewDTO) {
        ReviewDTO createdReview = reviewService.addReview(reviewDTO);
        return ResponseEntity.ok(createdReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ride/{rideId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByRideId(@PathVariable Integer rideId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByRideId(rideId);
        return ResponseEntity.ok(reviews);
    }
}
