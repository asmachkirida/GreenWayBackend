package ma.internship.greenway.service;

import ma.internship.greenway.dto.ReviewDTO;
import ma.internship.greenway.entity.Review;
import ma.internship.greenway.entity.Ride;
import ma.internship.greenway.repository.ReviewRepository;
import ma.internship.greenway.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RideRepository rideRepository;

    public ReviewDTO addReview(ReviewDTO reviewDTO) {
        Ride ride = rideRepository.findById(reviewDTO.getRideId())
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        Review review = new Review(null, reviewDTO.getDetails(), ride);
        review = reviewRepository.save(review);

        return new ReviewDTO(review.getId(), review.getDetails(), review.getRide().getId());
    }

    public void deleteReview(Integer id) {
        reviewRepository.deleteById(id);
    }

    public List<ReviewDTO> getReviewsByRideId(Integer rideId) {
        List<Review> reviews = reviewRepository.findByRideId(rideId);
        return reviews.stream()
                .map(review -> new ReviewDTO(review.getId(), review.getDetails(), review.getRide().getId()))
                .collect(Collectors.toList());
    }
}
