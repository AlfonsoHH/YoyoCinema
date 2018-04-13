package com.example.alfonsohernandez.yoyocinema.presentation.profile

import com.example.alfonsohernandez.yoyocinema.domain.interactors.nstack.SetNstackLanguageInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.preference.GetUserProfileInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.preference.SaveUserProfileInteractor
import com.example.alfonsohernandez.yoyocinema.domain.models.UserProfile
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

class ProfilePresenterTest {

    @Mock
    private lateinit var view: ProfileFragment

    @Mock
    private lateinit var getUser: GetUserProfileInteractor

    @Mock
    private lateinit var saveUser: SaveUserProfileInteractor

    @Mock
    private lateinit var setNStack: SetNstackLanguageInteractor

    private lateinit var presenter: ProfilePresenter

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        val user = UserProfile(null,"Alfonso",null,null, null)

        doReturn(user)
                .`when`(getUser)
                .getProfile()

        presenter = ProfilePresenter(getUser,saveUser,setNStack)
        presenter.setView(view)
    }

    @Test
    fun setView_ShouldPass(){
        val user = UserProfile(null,"Alfonso",null,null, null)

        verify(view).setData(user)
    }

    @Test
    fun saveLanguage() {
        val user = UserProfile(null,"Alfonso",null,null, null)

        doReturn(user)
                .`when`(getUser)
                .getProfile()

        presenter.saveLanguage(Mockito.mock(Locale::class.java))

        verify(saveUser).save(user)
    }
}