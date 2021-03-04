package com.example.mvvmtests.ui.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmtests.App
import com.example.mvvmtests.adapter.DiffAdapter
import com.example.mvvmtests.databinding.FragmentCardListBinding
import com.example.mvvmtests.ui.BaseFragment
import com.example.mvvmtests.ui.Screens
import com.example.mvvmtests.visibleOrGone
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class CardListFragment : BaseFragment() {

    @Inject
    lateinit var vmFactory: CardListViewModelFactory

    @Inject
    lateinit var router: Router

    private val viewModel by viewModels<CardListViewModel> {
        vmFactory
    }

    private lateinit var binding: FragmentCardListBinding

    private val adapter = DiffAdapter(CardListAdapterDelegates.cardListItem {
        router.navigateTo(Screens.CardsDetails(it.id))
    })

    override fun onAttach(context: Context) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.cardsList) {
            adapter = this@CardListFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.btnRetry.setOnClickListener {
            viewModel.loadCards()
        }
    }

    override fun onStart() {
        super.onStart()

        inStartStopScope {
            viewModel.cardsObservable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        adapter.items = it
                    }
        }

        inStartStopScope {
            viewModel.progressObservable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        binding.progress.visibleOrGone(it)
                    }
        }

        inStartStopScope {
            viewModel.retryObservable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        binding.btnRetry.visibleOrGone(it)
                    }
        }

        inStartStopScope {
            viewModel.emptyObservable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        binding.emptyMessage.visibleOrGone(it)
                    }
        }
    }
}