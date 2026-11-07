package com.vendorsphere.vendorsphere_backend.Vendor.exception;

public class VendorNotFoundException extends RuntimeException {
    public VendorNotFoundException(String message) {
        super(message);
    }
}
