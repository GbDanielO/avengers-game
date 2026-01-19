package br.com.avengers.adapters.in.web;

import br.com.avengers.application.DLQListenerControl;
import br.com.avengers.application.DLQRedriveService;
import br.com.avengers.ports.in.DLQMessagePortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/dlq")
public class DLQAdminController {

    private final DLQListenerControl dlqListenerControl;
    private final DLQMessagePortIn redriveService;

    @Autowired
    public DLQAdminController(
            DLQListenerControl dlqListenerControl,
            DLQRedriveService redriveService
    ) {
        this.dlqListenerControl = dlqListenerControl;
        this.redriveService = redriveService;
    }

    @PostMapping("/listener/start")
    public ResponseEntity<Void> startListener() {
        dlqListenerControl.startDlqListener();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/listener/stop")
    public ResponseEntity<Void> stopListener() {
        dlqListenerControl.stopDlqListener();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listener/status")
    public ResponseEntity<Boolean> status() {
        return ResponseEntity.ok(dlqListenerControl.isRunning());
    }

    @PostMapping("/redrive/{id}")
    public ResponseEntity<Void> redrive(@PathVariable String id,
                                        @RequestBody String jsonCorrigido) {
        redriveService.redriveManual(id, jsonCorrigido);
        return ResponseEntity.accepted().build();
    }
}
