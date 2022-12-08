package ru.nsu.wallet.controller

import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
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
import ru.nsu.wallet.dto.card.OrderedCardsResponse
import ru.nsu.wallet.dto.card.RemoveCardRequest
import ru.nsu.wallet.service.CardService
import ru.nsu.wallet.service.mapper.CardMapper

@RestController
@RequestMapping("/card")
@Tag(name = "Работа со скидочными картами")
class CardController(
    private val cardService: CardService,
    private val cardMapper: CardMapper
) {

    @GetMapping("/ordered-list")
    @ApiResponse(description = "Получить приоритезированный список скидочных карт. Требуется авторизация.")
    fun getCardListByCategory(
        @RequestParam category: String,
        @RequestParam lon: Double,
        @RequestParam lat: Double
    ): ResponseEntity<OrderedCardsResponse> {
        val orderedCards = cardService.getCardListOrderedByCategory(lon, lat, category, getUserIdFromSecurityContext())
        val result = OrderedCardsResponse(
            nearestCards = orderedCards.nearestCards.stream().map { card -> cardMapper.mapEntityToDto(card) }.toList(),
            anotherCards = orderedCards.anotherCards.stream().map { card -> cardMapper.mapEntityToDto(card) }.toList()
        )

        return ResponseEntity.ok(result)
    }

    @PostMapping
    @ApiResponse(description = "Добавить скидочную карту. Требуется авторизация.")
    fun addCard(@RequestBody addCardRequest: AddCardRequest): ResponseEntity<Nothing> {
        cardService.addCard(addCardRequest, getUserIdFromSecurityContext())
        return ResponseEntity.ok().build()
    }

    @DeleteMapping
    @ApiResponse(description = "Удалить скидочную карту. Требуется авторизация.")
    fun removeCard(@RequestBody removeCardRequest: RemoveCardRequest): ResponseEntity<Nothing> {
        cardService.removeCard(removeCardRequest, getUserIdFromSecurityContext())
        return ResponseEntity.ok().build()
    }

    private fun getUserIdFromSecurityContext(): Int =
        SecurityContextHolder.getContext().authentication.credentials as Int
}
