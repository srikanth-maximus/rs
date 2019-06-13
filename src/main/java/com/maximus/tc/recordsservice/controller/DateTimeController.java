/**
 * 
 */
package com.maximus.tc.recordsservice.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author srikanth.maximus
 *
 */
@RestController
@RequestMapping("/datetime")
public class DateTimeController {

  @GetMapping("")
  public ResponseEntity<ZonedDateTime> getCurrentDate() {
    return ResponseEntity.ok(ZonedDateTime.now());
  }

  @GetMapping("/{zoneId}")
  public ResponseEntity<ZonedDateTime> getCurrentDateTimeInZone(@PathVariable String zoneId) {
    return ResponseEntity.ok(ZonedDateTime.now(ZoneId.of(zoneId)));
  }
}
