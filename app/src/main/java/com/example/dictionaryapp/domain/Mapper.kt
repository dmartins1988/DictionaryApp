package com.example.dictionaryapp.domain

import com.example.dictionaryapp.data.local.entity.WordInfoEntity
import com.example.dictionaryapp.data.remote.dto.DefinitionDto
import com.example.dictionaryapp.data.remote.dto.MeaningDto
import com.example.dictionaryapp.data.remote.dto.WordInfoDto
import com.example.dictionaryapp.domain.model.Definition
import com.example.dictionaryapp.domain.model.Meaning
import com.example.dictionaryapp.domain.model.WordInfo

fun DefinitionDto.toDefinition(): Definition {
    return Definition(
        antonyms = antonyms,
        definition = definition,
        example = example,
        synonyms = synonyms
    )
}

fun MeaningDto.toMeaning(): Meaning {
    return Meaning(
        definitions = definitions.map { it.toDefinition() },
        partOfSpeech = partOfSpeech
    )
}

fun WordInfoDto.toWordInfo(): WordInfo {
    return WordInfo(
        meanings = meanings.map { it.toMeaning() },
        origin = origin,
        phonetic = phonetic,
        word = word
    )
}

fun WordInfoDto.toWordInfoEntity(): WordInfoEntity {
    return WordInfoEntity(
        meanings = meanings.map { it.toMeaning() },
        origin = origin ?: "",
        phonetic = phonetic ?: "",
        word = word
    )
}

fun WordInfoEntity.toWordInfo(): WordInfo {
    return WordInfo(
        meanings = meanings,
        origin = origin,
        phonetic = phonetic,
        word = word
    )
}