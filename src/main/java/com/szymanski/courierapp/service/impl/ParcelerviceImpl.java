package com.szymanski.courierapp.service.impl;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import com.szymanski.courierapp.data.db.entity.Label;
import com.szymanski.courierapp.data.db.entity.Parcel;
import com.szymanski.courierapp.data.db.repository.LabelRepository;
import com.szymanski.courierapp.data.db.repository.ParcelRepository;
import com.szymanski.courierapp.exception.LabelNotFoundException;
import com.szymanski.courierapp.exception.ParcelAlreadyExistException;
import com.szymanski.courierapp.exception.ParcelNotFoundException;
import com.szymanski.courierapp.model.LabelResponse;
import com.szymanski.courierapp.model.ParcelResponse;
import com.szymanski.courierapp.model.ParcelStatus;
import com.szymanski.courierapp.service.ParcelService;

@Service
public class ParcelerviceImpl implements ParcelService {

  @Autowired
  private LabelRepository labelDao;

  @Autowired
  private ParcelRepository parcelDao;

  @Override
  public Collection<LabelResponse> getAllLabels() {
    return this.labelDao.findAll().stream().map(ParcelerviceImpl::entityToDto)
        .collect(Collectors.toList());
  }

  @Override
  public LabelResponse getLabel(final Long labelId) throws LabelNotFoundException {
    return this.labelDao.findById(labelId).map(ParcelerviceImpl::entityToDto)
        .orElseThrow(LabelNotFoundException::new);
  }

  @Override
  public ParcelResponse create(final Long labelId)
      throws LabelNotFoundException, ParcelAlreadyExistException {
    final LabelResponse labelDto = this.getLabel(labelId);

    final Parcel probe = new Parcel();
    probe.setLabelId(labelDto.getId());
    if (this.parcelDao.exists(Example.of(probe))) {
      throw new ParcelAlreadyExistException();
    }

    final Parcel parcel = new Parcel();
    parcel.setStatus(ParcelStatus.ON_ITS_WAY.name());
    parcel.setLabelId(labelDto.getId());

    return entityToDto(this.parcelDao.save(parcel));
  }

  @Override
  public ParcelResponse getParcel(final Long labelId) throws ParcelNotFoundException {
    return this.parcelDao.findByLabelId(labelId).map(ParcelerviceImpl::entityToDto)
        .orElseThrow(ParcelNotFoundException::new);
  }

  @Override
  public ParcelResponse changeStatus(final Long labelId, final ParcelStatus status)
      throws ParcelNotFoundException {
    final Parcel parcel =
        this.parcelDao.findByLabelId(labelId).orElseThrow(ParcelNotFoundException::new);
    parcel.setStatus(status.name());
    return entityToDto(this.parcelDao.save(parcel));
  }

  private static LabelResponse entityToDto(final Label entity) {
    final LabelResponse response = new LabelResponse();
    response.setId(entity.getId());
    response.setReceiver(entity.getReceiver());
    response.setTarget(entity.getTarget());
    response.setSize(entity.getSize());
    return response;
  }

  private static ParcelResponse entityToDto(final Parcel parcel) {
    final ParcelResponse response = new ParcelResponse();
    response.setId(parcel.getId());
    response.setLabelId(parcel.getLabelId());
    response.setStatus(parcel.getStatus());
    return response;
  }



}
