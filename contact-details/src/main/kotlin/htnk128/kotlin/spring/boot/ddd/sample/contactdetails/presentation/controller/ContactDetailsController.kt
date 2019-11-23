package htnk128.kotlin.spring.boot.ddd.sample.contactdetails.presentation.controller

import htnk128.kotlin.spring.boot.ddd.sample.contactdetails.application.dto.ContactDetailsDTO
import htnk128.kotlin.spring.boot.ddd.sample.contactdetails.application.service.ContactDetailsService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Api("連作先を管理するAPI", tags = ["ContactDetails"])
@RestController
@RequestMapping("/contact-details")
class ContactDetailsController(private val contactDetailsService: ContactDetailsService) {

    @ApiOperation("指定された顧客のすべての連絡先情報を取得する")
    @GetMapping("")
    fun findAll(
        @ApiParam(value = "顧客ID", name = "customerId", example = "customer01", required = true)
        @RequestParam customerId: String
    ): ContactDetailsResponses =
        ContactDetailsResponses(
            contactDetailsService.findAll(customerId).map { it.toResponse() })

    @ApiOperation("連絡先を作成する")
    @PostMapping("", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody request: ContactDetailsCreateRequest
    ): ContactDetailsResponse =
        contactDetailsService.create(request.customerId, request.telephoneNumber).toResponse()

    @ApiOperation("連絡先を更新する")
    @PutMapping("/{contactDetailsId}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun update(
        @ApiParam(value = "連絡先ID", required = true, example = "contactDetails01")
        @PathVariable contactDetailsId: String,
        @RequestBody request: ContactDetailsUpdateRequest
    ): ContactDetailsResponse =
        contactDetailsService.update(contactDetailsId, request.telephoneNumber).toResponse()
}

data class ContactDetailsCreateRequest(
    @ApiModelProperty(value = "顧客ID", name = "customerId", example = "customer01", required = true, position = 1)
    val customerId: String,
    @ApiModelProperty(
        value = "電話番号", name = "telephoneNumber", example = "00000000000", required = true, position = 2
    )
    val telephoneNumber: String
)

data class ContactDetailsUpdateRequest(
    @ApiModelProperty(
        value = "電話番号", name = "telephoneNumber", example = "00000000000", required = true, position = 2
    )
    val telephoneNumber: String
)

data class ContactDetailsResponse(
    @ApiModelProperty(
        value = "連作先ID", name = "contactDetailsId", example = "contactDetails01", required = true, position = 1
    )
    val contactDetailsId: String,
    @ApiModelProperty(value = "顧客ID", name = "customerId", example = "customer01", required = true, position = 2)
    val customerId: String,
    @ApiModelProperty(
        value = "電話番号", name = "telephoneNumber", example = "00000000000", required = true, position = 3
    )
    val telephoneNumber: String
)

data class ContactDetailsResponses(
    val data: List<ContactDetailsResponse>
)

private fun ContactDetailsDTO.toResponse(): ContactDetailsResponse =
    ContactDetailsResponse(
        contactDetailsId,
        customerId,
        telephoneNumber
    )