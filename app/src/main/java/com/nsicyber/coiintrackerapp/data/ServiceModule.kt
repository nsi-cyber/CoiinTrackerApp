package com.nsicyber.coiintrackerapp.data

import com.nsicyber.coiintrackerapp.service.BackgroundTrackerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import javax.inject.Singleton

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideBackgroundTrackerService(): BackgroundTrackerService = BackgroundTrackerService()
}