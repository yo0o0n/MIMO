package com.mimo.android.screens.firstsettingfunnels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mimo.android.FirstSettingFunnelsViewModel
import com.mimo.android.components.Button
import com.mimo.android.R

@Preview
@Composable
fun TestFunnel(
    firstSettingFunnelsViewModel: FirstSettingFunnelsViewModel? = null
){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Button(text = "first setting start", onClick = {
            firstSettingFunnelsViewModel?.updateCurrentStep(R.string.first_setting_start_funnel)
        })
        Button(text = "qr code scan", onClick = {
            firstSettingFunnelsViewModel?.updateCurrentStep(R.string.qr_code_scan_funnel)
        })
        Button(text = "hub find waiting", onClick = {
            firstSettingFunnelsViewModel?.updateCurrentStep(R.string.hub_find_waiting_funnel)
        })
        Button(text = "if first find hub then go main", onClick = {
            firstSettingFunnelsViewModel?.updateCurrentStep(R.string.if_first_find_hub_then_go_main_funnel)
        })
        Button(text = "if find new hub then go register loc", onClick = {
            firstSettingFunnelsViewModel?.updateCurrentStep(R.string.if_find_new_hub_then_go_register_location_funnel)
        })
        Button(text = "auto register location", onClick = {
            firstSettingFunnelsViewModel?.updateCurrentStep(R.string.auto_register_location_funnel)
        })
        Button(text = "make location alias", onClick = {
            firstSettingFunnelsViewModel?.updateCurrentStep(R.string.make_location_alias_funnel)
        })
        Button(text = "check entered hub info", onClick = {
            firstSettingFunnelsViewModel?.updateCurrentStep(R.string.check_entered_hub_info_funnel)
        })
        Button(text = "redirect_main_after_register_hub_and_location", onClick = {
            firstSettingFunnelsViewModel?.updateCurrentStep(R.string.redirect_main_after_register_hub_and_location)
        })
    }
}