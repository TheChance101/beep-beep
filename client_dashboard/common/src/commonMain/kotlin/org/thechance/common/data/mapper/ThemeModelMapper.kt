package org.thechance.common.data.mapper

import org.thechance.common.domain.entity.ThemeMode

fun toThemeMode(mode: Boolean) = if (mode) ThemeMode.DARK else ThemeMode.LIGHT

fun isDarkMode(mode: ThemeMode) = mode == ThemeMode.DARK

