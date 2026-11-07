package com.vendorsphere.vendorsphere_backend.Vendor.exception;

public class VendorAlreadyExistsException extends RuntimeException {
    public VendorAlreadyExistsException(String message) {
        super(message);
    }
}
