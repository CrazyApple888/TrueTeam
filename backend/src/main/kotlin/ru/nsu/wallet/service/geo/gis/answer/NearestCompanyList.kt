package ru.nsu.wallet.service.geo.gis.answer

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class NearestCompanyList(
    val items: List<NearestCompany>
)
