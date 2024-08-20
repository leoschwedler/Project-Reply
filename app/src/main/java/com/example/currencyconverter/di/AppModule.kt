package com.example.currencyconverter.di

import android.content.Context
import com.example.currencyconverter.data.db.UserDAO
import com.example.currencyconverter.data.remote.ExchangeRateApi
import com.example.currencyconverter.data.repository.ConversionRepositoryImpl
import com.example.currencyconverter.data.repository.CurrencyRepositoryImpl
import com.example.currencyconverter.data.repository.DateRepositoryImpl
import com.example.currencyconverter.data.repository.ExchangeRatesRepositoryImpl
import com.example.currencyconverter.data.repository.LoginRepositoryImpl
import com.example.currencyconverter.data.repository.SignupRepositoryImpl
import com.example.currencyconverter.domain.repository.ConversionRepository
import com.example.currencyconverter.domain.repository.CurrencyRepository
import com.example.currencyconverter.domain.repository.DateRepository
import com.example.currencyconverter.domain.repository.ExchangeRatesRepository
import com.example.currencyconverter.domain.repository.LoginRepository
import com.example.currencyconverter.domain.repository.SignupRepository
import com.example.currencyconverter.domain.usecase.ConvertCurrencyUseCase
import com.example.currencyconverter.domain.usecase.GetCurrenciesUseCase
import com.example.currencyconverter.domain.usecase.GetDateUseCase
import com.example.currencyconverter.domain.usecase.GetExchangeRatesUseCase
import com.example.currencyconverter.domain.usecase.LoginUseCase
import com.example.currencyconverter.domain.usecase.SignupUseCase
import com.example.currencyconverter.util.Const
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideExchangeRateAPI(retrofit: Retrofit): ExchangeRateApi {
        return retrofit.create(ExchangeRateApi::class.java)
    }

    @Provides
    fun provideUserDAO(@ApplicationContext context: Context): UserDAO {
        return UserDAO(context)
    }

    // Repositories
    @Provides
    fun provideLoginRepository(userDAO: UserDAO): LoginRepository {
        return LoginRepositoryImpl(userDAO)
    }

    @Provides
    fun provideSignupRepository(userDAO: UserDAO): SignupRepository {
        return SignupRepositoryImpl(userDAO)
    }

    @Provides
    fun provideConversionRepository(exchangeRateApi: ExchangeRateApi): ConversionRepository {
        return ConversionRepositoryImpl(exchangeRateApi)
    }

    @Provides
    fun provideCurrencyRepository(exchangeRateApi: ExchangeRateApi): CurrencyRepository {
        return CurrencyRepositoryImpl(exchangeRateApi)
    }

    @Provides
    fun provideExchangeRatesRepository(exchangeRateApi: ExchangeRateApi): ExchangeRatesRepository {
        return ExchangeRatesRepositoryImpl(exchangeRateApi)
    }

    @Provides
    fun provideDateRepository(exchangeRateApi: ExchangeRateApi): DateRepository {
        return DateRepositoryImpl(exchangeRateApi)
    }

    // Use Cases
    @Provides
    fun provideLoginUseCase(loginRepository: LoginRepository): LoginUseCase {
        return LoginUseCase(loginRepository)
    }

    @Provides
    fun provideSignupUseCase(signupRepository: SignupRepository): SignupUseCase {
        return SignupUseCase(signupRepository)
    }

    @Provides
    fun provideConvertCurrencyUseCase(conversionRepository: ConversionRepository): ConvertCurrencyUseCase {
        return ConvertCurrencyUseCase(conversionRepository)
    }

    @Provides
    fun provideGetCurrenciesUseCase(currencyRepository: CurrencyRepository): GetCurrenciesUseCase {
        return GetCurrenciesUseCase(currencyRepository)
    }

    @Provides
    fun provideGetExchangeRatesUseCase(exchangeRatesRepository: ExchangeRatesRepository): GetExchangeRatesUseCase {
        return GetExchangeRatesUseCase(exchangeRatesRepository)
    }

    @Provides
    fun provideGetDateUseCase(dateRepository: DateRepository): GetDateUseCase {
        return GetDateUseCase(dateRepository)
    }
}
