package com.example.mvvmtests.ui.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mvvmtests.App
import com.example.mvvmtests.databinding.FragmentCardDetailsBinding
import com.example.mvvmtests.ui.BaseFragment
import com.example.mvvmtests.visibleOrGone
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class CardDetailsFragment : BaseFragment() {

    companion object {
        private const val ARG_CARD_ID = "ARG_CARD_ID"

        fun instance(cardId: String): CardDetailsFragment = CardDetailsFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_CARD_ID, cardId)
            }
        }
    }

    @Inject
    lateinit var vmFactory: CardDetailsViewModelFactory

    private val viewModel by viewModels<CardDetailsViewModel> {
        vmFactory
    }

    private lateinit var binding: FragmentCardDetailsBinding

    override fun onAttach(context: Context) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val cardId = arguments?.getString(ARG_CARD_ID)
                ?: throw IllegalArgumentException("Won't work unless card id is provided")
            viewModel.loadCardDetails(cardId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        inStartStopScope {
            viewModel.cardDetailsObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val (name, number, logo) = it

                    with(binding) {
                        contentContainer.visibleOrGone(true)

                        cardNumber.text = number
                        cardName.text = name
                        cardLogo.setImageResource(logo)
                    }
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
                    binding.contentContainer.visibleOrGone(it.not())
                    binding.btnRetry.visibleOrGone(it)
                }
        }
    }
}