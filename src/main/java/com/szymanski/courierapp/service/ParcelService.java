package com.szymanski.courierapp.service;

import java.util.Collection;
import com.szymanski.courierapp.exception.LabelNotFoundException;
import com.szymanski.courierapp.exception.ParcelAlreadyExistException;
import com.szymanski.courierapp.exception.ParcelNotFoundException;
import com.szymanski.courierapp.model.LabelResponse;
import com.szymanski.courierapp.model.ParcelResponse;
import com.szymanski.courierapp.model.ParcelStatus;

public interface ParcelService {

  Collection<LabelResponse> getAllLabels();

  LabelResponse getLabel(Long labelId) throws LabelNotFoundException;

  ParcelResponse getParcel(Long labelId) throws ParcelNotFoundException;

  ParcelResponse create(Long labelId) throws LabelNotFoundException, ParcelAlreadyExistException;

  ParcelResponse changeStatus(Long labelId, ParcelStatus status) throws ParcelNotFoundException;

}
