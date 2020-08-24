/*
 * Copyright (c) 2015, Nordic Semiconductor
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.example.ciabluetooth.uart;

import android.bluetooth.BluetoothDevice;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import com.example.ciabluetooth.R;
import com.example.ciabluetooth.profile.BleProfileService;
import com.example.ciabluetooth.profile.BleProfileServiceReadyActivity;

import java.util.UUID;

public class UARTActivity extends BleProfileServiceReadyActivity<UARTService.UARTBinder> implements UARTInterface {
	private final static String TAG = "UARTActivity";

	/** The current configuration. */
	private SlidingPaneLayout slider;
	private View container;
	private UARTService.UARTBinder serviceBinder;

	@Override
	protected Class<? extends BleProfileService> getServiceClass() {
		return UARTService.class;
	}

	@Override
	protected int getLoggerProfileTitle() {
		return R.string.uart_feature_title;
	}

	@Override
	protected Uri getLocalAuthorityLogger() {
		return UARTLocalLogContentProvider.AUTHORITY_URI;
	}

	@Override
	protected void setDefaultUI() {
		// empty
	}

	@Override
	protected void onServiceBound(final UARTService.UARTBinder binder) {
		serviceBinder = binder;
	}

	@Override
	protected void onServiceUnbound() {
		serviceBinder = null;
	}

	@Override
	protected void onInitialize(final Bundle savedInstanceState) {

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onCreateView(final Bundle savedInstanceState) {
		setContentView(R.layout.activity_feature_uart);

		container = findViewById(R.id.container);
		// Setup the sliding pane if it exists
		final SlidingPaneLayout slidingPane = slider = findViewById(R.id.sliding_pane);
		if (slidingPane != null) {
			slidingPane.setSliderFadeColor(Color.TRANSPARENT);
//			slidingPane.setShadowResourceLeft(R.drawable.shadow_r);
			slidingPane.setPanelSlideListener(new SlidingPaneLayout.SimplePanelSlideListener() {
				@Override
				public void onPanelClosed(final View panel) {
					// Close the keyboard
					final UARTLogFragment logFragment = (UARTLogFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_log);
					logFragment.onFragmentHidden();
				}
			});
		}
	}

	@Override
	protected void onViewCreated(final Bundle savedInstanceState) {
	}

	@Override
	protected void onRestoreInstanceState(final @NonNull Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

	}

	@Override
	public void onSaveInstanceState(@NonNull final Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onServicesDiscovered(@NonNull final BluetoothDevice device, final boolean optionalServicesFound) {
		// do nothing
	}

	@Override
	public void onDeviceSelected(@NonNull final BluetoothDevice device, final String name) {
		// The super method starts the service
		super.onDeviceSelected(device, name);

		// Notify the log fragment about it
		final UARTLogFragment logFragment = (UARTLogFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_log);
		logFragment.onServiceStarted();
	}

	@Override
	protected int getDefaultDeviceName() {
		return R.string.uart_default_name;
	}

	@Override
	protected int getAboutTextId() {
		return R.string.uart_about_text;
	}

	@Override
	protected UUID getFilterUUID() {
		return null; // not used
	}

	@Override
	public void send(final String text) {
		if (serviceBinder != null)
			serviceBinder.send(text);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
