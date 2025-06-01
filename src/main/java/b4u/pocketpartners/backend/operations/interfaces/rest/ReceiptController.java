package b4u.pocketpartners.backend.operations.interfaces.rest;

import b4u.pocketpartners.backend.operations.domain.exceptions.ReceiptNotFoundException;
import b4u.pocketpartners.backend.operations.domain.model.commands.DeleteReceiptCommand;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetAllReceiptsByExpenseIdQuery;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetAllReceiptsByPaymentIdQuery;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetReceiptByIdQuery;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetReceiptTextByIdQuery;
import b4u.pocketpartners.backend.operations.domain.services.ReceiptCommandService;
import b4u.pocketpartners.backend.operations.domain.services.ReceiptQueryService;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.CreateExpenseReceiptResource;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.CreatePaymentReceiptResource;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.ReceiptResource;
import b4u.pocketpartners.backend.operations.interfaces.rest.transform.CreateReceiptCommandFromResourceAssembler;
import b4u.pocketpartners.backend.operations.interfaces.rest.transform.ReceiptResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Tag(name = "Receipts", description = "Receipts management endpoints")
@RestController
@AllArgsConstructor
@RequestMapping(value="/api/v1/receipts", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReceiptController {
    private ReceiptQueryService receiptQueryService;
    private ReceiptCommandService receiptCommandService;

    @GetMapping("/{receiptId}")
    @Operation(summary = "Get receipt by id")
    public ResponseEntity<ReceiptResource> getReceiptById(@PathVariable Long receiptId){
        var query = new GetReceiptByIdQuery(receiptId);
        var receipt = receiptQueryService.handle(query).orElseThrow(()->new ReceiptNotFoundException(receiptId));
        var receiptResouce = ReceiptResourceFromEntityAssembler.toResourceFromEntity(receipt);

        return ResponseEntity.ok(receiptResouce);
    }

    @GetMapping("/payment/{paymentId}")
    @Operation(summary = "Get all receipts by paymentId")
    public ResponseEntity<List<ReceiptResource>> getReceiptsByPaymentId(@PathVariable Long paymentId){
        var query = new GetAllReceiptsByPaymentIdQuery(paymentId);
        var receipts = receiptQueryService.handle(query);
        var receiptsResources = receipts.stream().map(ReceiptResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(receiptsResources);
    }

    @PostMapping("/payment")
    @Operation(summary = "Create a new receipt for a payment")
    public ResponseEntity<ReceiptResource> createPaymentReceipt(@Validated @RequestBody CreatePaymentReceiptResource resource){
        var command = CreateReceiptCommandFromResourceAssembler.toCommandFromResource(resource);
        var receipt = receiptCommandService.handle(command);
        var receiptResouce = ReceiptResourceFromEntityAssembler.toResourceFromEntity(receipt);

        return new ResponseEntity(receiptResouce, HttpStatus.CREATED);
    }

    @GetMapping("/expense/{expenseId}")
    @Operation(summary = "Get all receipts by expenseId")
    public ResponseEntity<List<ReceiptResource>> getReceiptsByExpenseId(@PathVariable Long expenseId){
        var query = new GetAllReceiptsByExpenseIdQuery(expenseId);
        var receipts = receiptQueryService.handle(query);
        var receiptsResources = receipts.stream().map(ReceiptResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(receiptsResources);
    }

    @PostMapping("/expense")
    @Operation(summary = "Create a new receipt for a expense")
    public ResponseEntity<ReceiptResource> createExpenseReceipt(@Validated @RequestBody CreateExpenseReceiptResource resource){
        var command = CreateReceiptCommandFromResourceAssembler.toCommandFromResource(resource);
        var receipt = receiptCommandService.handle(command);
        var receiptResouce = ReceiptResourceFromEntityAssembler.toResourceFromEntity(receipt);

        return new ResponseEntity(receiptResouce, HttpStatus.CREATED);
    }


    @GetMapping("/text/{receiptId}")
    @Operation(summary = "Get text from a receipt by receiptId")
    public ResponseEntity<String> getTextFromReceiptByid(@PathVariable Long receiptId) throws IOException {
        var query = new GetReceiptTextByIdQuery(receiptId);
        String textFromReceipt = receiptQueryService.handle(query);
        return ResponseEntity.ok(textFromReceipt);
    }

    @DeleteMapping("/{receiptId}")
    @Operation(summary = "Delete a receipt by id")
    public ResponseEntity<Void> deleteReceiptById(@PathVariable Long receiptId) {
        var command = new DeleteReceiptCommand(receiptId);
        receiptCommandService.handle(command);
        return ResponseEntity.noContent().build();
    }

}
