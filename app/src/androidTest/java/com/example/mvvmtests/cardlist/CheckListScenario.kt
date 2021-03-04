package com.example.mvvmtests.cardlist

import com.kaspersky.kaspresso.testcases.api.scenario.Scenario
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext

object CheckListScenario : Scenario() {

    override val steps: TestContext<Unit>.() -> Unit = {
        CardsListScreen {
            cardsList {
                firstChild<CardsListScreen.CardItem> {
                    cardNumber {
                        hasText("122c706a-bdc5-48bf-824b-c88c4ec687e1")
                    }

                    cardLogo {
                        hasDrawable(com.example.mvvmtests.R.drawable.ic_card3)
                    }
                }

                childAt<CardsListScreen.CardItem>(2) {
                    cardNumber {
                        hasText("95ade9ca-0d4a-4c07-9acf-2b1739bca6e4")
                    }
                    cardLogo {
                        hasDrawable(com.example.mvvmtests.R.drawable.ic_card2)
                    }
                }

                childAt<CardsListScreen.CardItem>(6) {
                    cardNumber {
                        hasText("0c9bb5d2-fb34-4a7d-bdd2-eedd858854bf")
                    }
                    cardLogo {
                        hasDrawable(com.example.mvvmtests.R.drawable.ic_card1)
                    }
                }
            }
        }
    }
}
