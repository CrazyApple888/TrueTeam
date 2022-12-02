package ru.nsu.wallet.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.nsu.wallet.dto.card.AddCardRequest
import ru.nsu.wallet.dto.card.CardDto
import ru.nsu.wallet.dto.card.RemoveCardRequest
import ru.nsu.wallet.service.CardService
import ru.nsu.wallet.service.mapper.CardMapper

@RestController
@RequestMapping("/card")
class CardController(
    private val cardService: CardService,
    private val cardMapper: CardMapper
) {

    @GetMapping("/ordered-list")
    fun getCardListByType(
        @RequestParam type: String,
        @RequestParam lon: Double,
        @RequestParam lat: Double
    ): ResponseEntity<List<CardDto>> {
        val orderedCards = cardService.getCardListOrderedByType(lon, lat, type, getUserIdFromSecurityContext())
        val result = orderedCards.map { card -> cardMapper.mapEntityToDto(card) }.toList()

        return ResponseEntity.ok(result)
    }

    @PostMapping
    fun addCard(@RequestBody addCardRequest: AddCardRequest): ResponseEntity<Nothing> {
        cardService.addCard(addCardRequest, getUserIdFromSecurityContext())
        return ResponseEntity.ok().build()
    }

    @DeleteMapping
    fun removeCard(@RequestBody removeCardRequest: RemoveCardRequest): ResponseEntity<Nothing> {
        cardService.removeCard(removeCardRequest, getUserIdFromSecurityContext())
        return ResponseEntity.ok().build()
    }

    private fun getUserIdFromSecurityContext(): Int {
        return SecurityContextHolder.getContext().authentication.credentials as Int
    }
}