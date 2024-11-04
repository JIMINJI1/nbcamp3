package com.sparta.schedule.exception;

public class ErrorResponse {
  private int status;
  private String errorMessage;

  public ErrorResponse(int status, String errorMessage) {
    this.status = status;
    this.errorMessage = errorMessage;
  }

  public int getStatus() {
    return status;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
