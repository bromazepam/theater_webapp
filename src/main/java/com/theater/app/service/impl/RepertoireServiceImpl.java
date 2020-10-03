package com.theater.app.service.impl;

import com.theater.app.domain.Repertoire;
import com.theater.app.domain.reportDAO.RepertoireReport;
import com.theater.app.domain.Seat;
import com.theater.app.exceptions.NotFoundException;
import com.theater.app.repository.RepertoireRepository;
import com.theater.app.repository.SeatRepository;
import com.theater.app.service.RepertoireService;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class RepertoireServiceImpl implements RepertoireService {

    private final MongoTemplate mongoTemplate;
    private RepertoireRepository repertoireRepository;
    private SeatRepository seatRepository;

    public RepertoireServiceImpl(MongoTemplate mongoTemplate, RepertoireRepository repertoireRepository, SeatRepository seatRepository) {
        this.mongoTemplate = mongoTemplate;
        this.repertoireRepository = repertoireRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public Repertoire save(Repertoire repertoire) {
        return repertoireRepository.save(repertoire);
    }

    @Override
    public List<Repertoire> findAll() {
        return (List<Repertoire>) repertoireRepository.findAll();
    }

    @Override
    public Repertoire findById(String id) {
        Optional<Repertoire> optionalRepertoire = repertoireRepository.findById(id);

        if (!optionalRepertoire.isPresent()) {
            throw new NotFoundException("Stage not found, For ID value: " + id);
        }
        return optionalRepertoire.get();
    }

    @Override
    public List<Repertoire> findByDate(Date date) {

        return repertoireRepository.findByProjectionDate(new java.sql.Date(date.getTime()));
    }

    @Override
    public void deleteById(String id) {
        repertoireRepository.deleteById(id);
    }

    @Override
    public int availableSeats(String id) {
        List<Seat> lst = seatRepository.findByStageIdAndReservedFalse(id);
        return lst.size();
    }

    @Override
    public List<Repertoire> findByPresentOrFutureDate(Date date) {

        return repertoireRepository.findByProjectionDateGreaterThanEqual(new java.sql.Date(date.getTime()));
    }

    @Override
    public List<RepertoireReport> findMonthlyAttendance() {

        Aggregation agg = newAggregation(
                project("stage.capacity", "availableSeats")
                .andExpression("month(projectionDate)").as("month")
                .andExpression("year(projectionDate)").as("year")
                .andExpression("stage.capacity - availableSeats").as("att"),
                group(fields("month").and("year")).count().as("total")
                .sum("att").as("attendance")

        );

        AggregationResults<RepertoireReport> results = mongoTemplate.aggregate(
                agg,"repertoire", RepertoireReport.class);
        return results.getMappedResults();
    }
}
