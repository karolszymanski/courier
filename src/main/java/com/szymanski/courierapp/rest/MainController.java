package com.szymanski.courierapp.rest;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.szymanski.courierapp.exception.LabelNotFoundException;
import com.szymanski.courierapp.exception.ParcelAlreadyExistException;
import com.szymanski.courierapp.exception.ParcelNotFoundException;
import com.szymanski.courierapp.model.LabelResponse;
import com.szymanski.courierapp.model.ParcelResponse;
import com.szymanski.courierapp.model.ParcelStatus;
import com.szymanski.courierapp.service.ParcelService;

@RestController
@RequestMapping(path = "/rest/v1")
public class MainController {

  private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

  @Autowired
  private ParcelService parcelService;

  @GetMapping(value = "/labels", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Collection<LabelResponse>> listAllLabels() {
    final Collection<LabelResponse> labels = this.parcelService.getAllLabels();
    for (final LabelResponse label : labels) {
      final Link selfLink = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(MainController.class).getLabel(label.getId()))
          .withSelfRel();
      label.add(selfLink);

      final Link parcelLink = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(MainController.class).getParcel(label.getId()))
          .withRel("parcel");
      label.add(parcelLink);
    }
    return ResponseEntity.ok(labels);
  }

  @GetMapping(value = "/label/{labelId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<LabelResponse> getLabel(@PathVariable("labelId") final Long labelId) {
    try {
      final LabelResponse label = this.parcelService.getLabel(labelId);
      final Link selfLink = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(MainController.class).getLabel(label.getId()))
          .withSelfRel();
      label.add(selfLink);


      final Link parcelLink = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(MainController.class).getParcel(label.getId()))
          .withRel("parcel");
      label.add(parcelLink);

      final Link labelsLink =
          WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MainController.class).listAllLabels())
              .withRel("labels");
      label.add(labelsLink);

      return ResponseEntity.ok(label);
    } catch (final LabelNotFoundException e) {
      LOG.error("Label with id {} not found", labelId);
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping(value = "/parcel/{labelId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ParcelResponse> getParcel(@PathVariable("labelId") final Long labelId) {
    try {
      final ParcelResponse parcel = this.parcelService.getParcel(labelId);

      final Link selfLink = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(MainController.class).getParcel(parcel.getLabelId()))
          .withSelfRel();
      parcel.add(selfLink);

      final Link labelLink = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(MainController.class).getLabel(parcel.getLabelId()))
          .withRel("label");
      parcel.add(labelLink);

      return ResponseEntity.ok(parcel);
    } catch (final ParcelNotFoundException e) {
      LOG.error("Parcel with label id {} not found", labelId);
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping(value = "/parcel/{labelId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ParcelResponse> createParcel(@PathVariable("labelId") final Long labelId) {
    try {

      final ParcelResponse parcel = this.parcelService.create(labelId);

      final Link selfLink = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(MainController.class).getParcel(parcel.getLabelId()))
          .withSelfRel();
      parcel.add(selfLink);

      final Link labelLink = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(MainController.class).getLabel(parcel.getLabelId()))
          .withRel("label");
      parcel.add(labelLink);

      return ResponseEntity.ok(parcel);
    } catch (final LabelNotFoundException e) {
      LOG.error("Label with id {} not found", labelId);
      return ResponseEntity.badRequest().build();
    } catch (final ParcelAlreadyExistException e) {
      LOG.error("Parcel with id {} already exists", labelId);

      final String href = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(MainController.class).getLabel(labelId)).withSelfRel()
          .getHref();

      return ResponseEntity.status(HttpStatus.CONFLICT).header(HttpHeaders.LOCATION, href).build();
    }
  }

  @PostMapping(value = "/parcel/{labelId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ParcelResponse> changeStatus(@PathVariable("labelId") final Long labelId,
      @RequestBody final String status) {

    final ParcelStatus parcelStatus = toStatus(status);
    if (null == parcelStatus) {
      LOG.error("Invalid status value passed {}", status);
      return ResponseEntity.badRequest().build();
    }

    try {
      final ParcelResponse parcel = this.parcelService.changeStatus(labelId, parcelStatus);

      final Link selfLink = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(MainController.class).getParcel(parcel.getLabelId()))
          .withSelfRel();
      parcel.add(selfLink);

      final Link labelLink = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(MainController.class).getLabel(parcel.getLabelId()))
          .withRel("label");
      parcel.add(labelLink);

      return ResponseEntity.ok(parcel);
    } catch (final ParcelNotFoundException e) {
      LOG.error("Parcel with label id {} not found", labelId);
      return ResponseEntity.badRequest().build();
    }

  }

  private static ParcelStatus toStatus(final String status) {
    try {
      return ParcelStatus.valueOf(status);
    } catch (Exception e) {
      return null;
    }
  }


}
