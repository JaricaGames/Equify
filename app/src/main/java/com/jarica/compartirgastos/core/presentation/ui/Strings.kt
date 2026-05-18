package com.jarica.compartirgastos.core.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.jarica.compartirgastos.R

// SplashScreen
val appName: String
    @Composable get() = stringResource(R.string.app_name)
val initalPhrase: String
    @Composable get() = stringResource(R.string.initial_phrase)
val buttonPhrase: String
    @Composable get() = stringResource(R.string.app_version)

// InitialScreen
val mainText: String
    @Composable get() = stringResource(R.string.main_text)
val secondaryText: String
    @Composable get() = stringResource(R.string.secondary_text)
val newGroupText: String
    @Composable get() = stringResource(R.string.new_group_text)
val joinGroupText: String
    @Composable get() = stringResource(R.string.join_group_text)

// NewGroupScreen
val next: String
    @Composable get() = stringResource(R.string.next)
val labelTextFieldNewGroupScreen: String
    @Composable get() = stringResource(R.string.label_group_name_field)
val newGroupStepLabel: String
    @Composable get() = stringResource(R.string.new_group_step_label)
val newGroupSubtitle: String
    @Composable get() = stringResource(R.string.new_group_subtitle)
val newGroupNextButton: String
    @Composable get() = stringResource(R.string.new_group_next_button)

// AddPeopleScreen
val labelTextFieldAddPeopleScreen: String
    @Composable get() = stringResource(R.string.label_add_people_field)
val addPeopleFromMainSubtitle: String
    @Composable get() = stringResource(R.string.add_people_from_main_subtitle)
val createText: String
    @Composable get() = stringResource(R.string.create_group_text)
val addPeopleText: String
    @Composable get() = stringResource(R.string.add_people_text)
val addEverybodyText: String
    @Composable get() = stringResource(R.string.add_everybody_text)
val addPeopleStepLabel: String
    @Composable get() = stringResource(R.string.add_people_step_label)
val addPeopleTitle: String
    @Composable get() = stringResource(R.string.add_people_title)
val addPeopleFieldLabel: String
    @Composable get() = stringResource(R.string.add_people_field_label)
val addPeopleOtherFieldLabel: String
    @Composable get() = stringResource(R.string.add_people_other_field_label)
val addPeopleHint: String
    @Composable get() = stringResource(R.string.add_people_hint)
val addPeopleEmptyTitle: String
    @Composable get() = stringResource(R.string.add_people_empty_title)
val addPeopleEmptySubtitle: String
    @Composable get() = stringResource(R.string.add_people_empty_subtitle)
val addPeopleMinButton: String
    @Composable get() = stringResource(R.string.add_people_min_button)
val addPeopleMembersLabel: String
    @Composable get() = stringResource(R.string.add_people_members_label)
val addPeoplePlaceholder: String
    @Composable get() = stringResource(R.string.add_people_placeholder)
val addPeopleMemberRole: String
    @Composable get() = stringResource(R.string.add_people_member_role)
val deleteContentDescription: String
    @Composable get() = stringResource(R.string.delete_content_description)

// GroupDetailsScreen (MainScreen)
val addCost: String
    @Composable get() = stringResource(R.string.add_cost)
val addPersonText: String
    @Composable get() = stringResource(R.string.add_person)
val addPay: String
    @Composable get() = stringResource(R.string.add_pay)
val costs: String
    @Composable get() = stringResource(R.string.costs)
val payments: String
    @Composable get() = stringResource(R.string.payments)
val resume: String
    @Composable get() = stringResource(R.string.resume)
val doTheCount: String
    @Composable get() = stringResource(R.string.do_the_count)
val addPeople: String
    @Composable get() = stringResource(R.string.add_people)
val totalCostText: String
    @Composable get() = stringResource(R.string.total_cost_text)

// AddCostScreen
val descriptionPlaceHolder: String
    @Composable get() = stringResource(R.string.description_placeholder)
val amountPlaceHolder: String
    @Composable get() = stringResource(R.string.amount_placeholder)
val fromText: String
    @Composable get() = stringResource(R.string.from_text)
val saveCost: String
    @Composable get() = stringResource(R.string.save_cost)
val splitTypeLabel: String
    @Composable get() = stringResource(R.string.split_type_label)
val splitLabel: String
    @Composable get() = stringResource(R.string.split_label)
val splitEqual: String
    @Composable get() = stringResource(R.string.split_equal)
val splitEqualAlt: String
    @Composable get() = stringResource(R.string.split_equal_alt)
val splitParts: String
    @Composable get() = stringResource(R.string.split_parts)
val splitPartsShort: String
    @Composable get() = stringResource(R.string.split_parts_short)
val splitPercentage: String
    @Composable get() = stringResource(R.string.split_percentage)

// NewGroupScreen / EditGroupNameScreen
val groupNameExample: String
    @Composable get() = stringResource(R.string.group_name_example)

// GroupsScreen
val groupsText: String
    @Composable get() = stringResource(R.string.groups_text)
val addNewGroupText: String
    @Composable get() = stringResource(R.string.add_new_group_text)
val groupsYourGroups: String
    @Composable get() = stringResource(R.string.groups_your_groups)
val groupsTotalLabel: String
    @Composable get() = stringResource(R.string.groups_total_label)

// AddPaymentScreen
val addPayment: String
    @Composable get() = stringResource(R.string.add_payment)
val addPaymentTitle: String
    @Composable get() = stringResource(R.string.add_payment_title)
val addPaymentSubtitle: String
    @Composable get() = stringResource(R.string.add_payment_subtitle)
val addPaymentAmountLabel: String
    @Composable get() = stringResource(R.string.add_payment_amount_label)
val addPaymentChangePeopleLabel: String
    @Composable get() = stringResource(R.string.add_payment_change_people_label)
val addPaymentFromTag: String
    @Composable get() = stringResource(R.string.add_payment_from_tag)
val addPaymentToTag: String
    @Composable get() = stringResource(R.string.add_payment_to_tag)
val addPaymentFromLabel: String
    @Composable get() = stringResource(R.string.add_payment_from_label)
val addPaymentToLabel: String
    @Composable get() = stringResource(R.string.add_payment_to_label)
val registerPaymentText: String
    @Composable get() = stringResource(R.string.register_payment_text)
val addPaymentSelectPerson: String
    @Composable get() = stringResource(R.string.add_payment_select_person)
val payForPlaceHolder: String
    @Composable get() = stringResource(R.string.pay_for_placeholder)
val payToPlaceHolder: String
    @Composable get() = stringResource(R.string.pay_to_placeholder)
val payForText: String
    @Composable get() = stringResource(R.string.pay_for_text)
val payToText: String
    @Composable get() = stringResource(R.string.pay_to_text)
val amountText: String
    @Composable get() = stringResource(R.string.amount_text)

// EditCostScreen
val editCost: String
    @Composable get() = stringResource(R.string.edit_cost)
val editCostDeleteLabel: String
    @Composable get() = stringResource(R.string.edit_cost_delete_label)
val editCostDeleteSub: String
    @Composable get() = stringResource(R.string.edit_cost_delete_sub)

// ConfigurationScreen
val configurationTextScreen: String
    @Composable get() = stringResource(R.string.configuration_text_screen)
val personalizationGroupText: String
    @Composable get() = stringResource(R.string.personalization_group_text)
val addPeopleConfigurationText: String
    @Composable get() = stringResource(R.string.add_people_configuration_text)
val administratePeopleConfigurationText: String
    @Composable get() = stringResource(R.string.administrate_people_configuration_text)
val groupMembersText: String
    @Composable get() = stringResource(R.string.group_members_text)
val otherText: String
    @Composable get() = stringResource(R.string.other_text)
val deleteGroupText: String
    @Composable get() = stringResource(R.string.delete_group_text)
val informationText: String
    @Composable get() = stringResource(R.string.information_text)
val aboutText: String
    @Composable get() = stringResource(R.string.about_text)
val feedbackText: String
    @Composable get() = stringResource(R.string.feedback_text)
val configAjustesTitle: String
    @Composable get() = stringResource(R.string.config_ajustes_title)
val configGrupoLabel: String
    @Composable get() = stringResource(R.string.config_grupo_label)
val configMiembrosLabel: String
    @Composable get() = stringResource(R.string.config_miembros_label)
val configInfoGroupEyebrow: String
    @Composable get() = stringResource(R.string.config_info_group_eyebrow)
val configNombreGrupoLabel: String
    @Composable get() = stringResource(R.string.config_nombre_grupo_label)
val configMiembrosEyebrow: String
    @Composable get() = stringResource(R.string.config_miembros_eyebrow)
val configDangerEyebrow: String
    @Composable get() = stringResource(R.string.config_danger_eyebrow)
val configDeleteSubLabel: String
    @Composable get() = stringResource(R.string.config_delete_sub_label)
val configMiembroRole: String
    @Composable get() = stringResource(R.string.config_miembro_role)
val addLabel: String
    @Composable get() = stringResource(R.string.add_label)
val appVersionFooter: String
    @Composable get() = stringResource(R.string.app_version_footer)

// CustomizeGroupScreen
val customizeGroupScreenText: String
    @Composable get() = stringResource(R.string.customize_group_screen_text)
val labelCustomizeGroupScreenText: String
    @Composable get() = stringResource(R.string.label_customize_group_screen_text)
val changeGroupName: String
    @Composable get() = stringResource(R.string.change_group_name)
val editGroupTitle: String
    @Composable get() = stringResource(R.string.edit_group_title)
val editGroupSubtitle: String
    @Composable get() = stringResource(R.string.edit_group_subtitle)
val editGroupCurrentNameLabel: String
    @Composable get() = stringResource(R.string.edit_group_current_name_label)
val editGroupHintText: String
    @Composable get() = stringResource(R.string.edit_group_hint_text)
val saveChangesText: String
    @Composable get() = stringResource(R.string.save_changes_text)

// AlertDialogs (ConfigurationScreen)
val mainAlertDialogText: String
    @Composable get() = stringResource(R.string.main_alert_dialog_text)
val alertDialogText: String
    @Composable get() = stringResource(R.string.alert_dialog_text)
val confirmAlertDialogText1: String
    @Composable get() = stringResource(R.string.confirm_alert_dialog_text1)
val confirmAlertDialogText2: String
    @Composable get() = stringResource(R.string.confirm_alert_dialog_text2)
val titleConfirmAlertDialogText: String
    @Composable get() = stringResource(R.string.title_confirm_alert_dialog_text)
val ok: String
    @Composable get() = stringResource(R.string.ok)
val cancel: String
    @Composable get() = stringResource(R.string.cancel)

// DoTheCountsScreen
val exportArrayListDoTheCountsText: String
    @Composable get() = stringResource(R.string.export_pdf_text)
val exportAdButtonText: String
    @Composable get() = stringResource(R.string.export_ad_button_text)
val doTheCountsSubtitleZero: String
    @Composable get() = stringResource(R.string.do_the_counts_subtitle_zero)
val doTheCountsNoDebts: String
    @Composable get() = stringResource(R.string.do_the_counts_no_debts)
val doTheCountsEmptyTitle: String
    @Composable get() = stringResource(R.string.do_the_counts_empty_title)
val doTheCountsEmptySubtitle: String
    @Composable get() = stringResource(R.string.do_the_counts_empty_subtitle)
val doTheCountsTransfersLabel: String
    @Composable get() = stringResource(R.string.do_the_counts_transfers_label)
val doTheCountsPayTag: String
    @Composable get() = stringResource(R.string.do_the_counts_pay_tag)
val doTheCountsReceiveTag: String
    @Composable get() = stringResource(R.string.do_the_counts_receive_tag)
val exportArrayListDoTheCountsLargeText: String
    @Composable get() = stringResource(R.string.export_pdf_large_text)
val noAppToOpenPDF: String
    @Composable get() = stringResource(R.string.no_app_to_open_pdf)

// EmptyState
val addFirstCost: String
    @Composable get() = stringResource(R.string.add_first_cost)
val emptyCostsTitle: String
    @Composable get() = stringResource(R.string.empty_costs_title)
val emptyCostsSubtitle: String
    @Composable get() = stringResource(R.string.empty_costs_subtitle)
val emptyPaymentsTitle: String
    @Composable get() = stringResource(R.string.empty_payments_title)
val emptyPaymentsSubtitle: String
    @Composable get() = stringResource(R.string.empty_payments_subtitle)
val emptyResumeTitle: String
    @Composable get() = stringResource(R.string.empty_resume_title)
val emptyResumeSubtitle: String
    @Composable get() = stringResource(R.string.empty_resume_subtitle)

// AboutEquifyScreen
val aboutScreenText: String
    @Composable get() = stringResource(R.string.about_screen_text)
val aboutScreenLongText: String
    @Composable get() = stringResource(R.string.about_screen_long_text)
val buttonAboutScreenText: String
    @Composable get() = stringResource(R.string.button_about_screen_text)
val aboutScreenLongText2: String
    @Composable get() = stringResource(R.string.about_screen_long_text2)
val aboutSubtitle: String
    @Composable get() = stringResource(R.string.about_subtitle)
val aboutVersionLabel: String
    @Composable get() = stringResource(R.string.about_version_label)
val aboutAuthorName: String
    @Composable get() = stringResource(R.string.about_author_name)
val aboutAuthorRole: String
    @Composable get() = stringResource(R.string.about_author_role)
val aboutAuthorMessage: String
    @Composable get() = stringResource(R.string.about_author_message)
val aboutRateGooglePlayText: String
    @Composable get() = stringResource(R.string.about_rate_google_play_text)
val aboutImproveEyebrow: String
    @Composable get() = stringResource(R.string.about_improve_eyebrow)
val aboutFeedbackLabel: String
    @Composable get() = stringResource(R.string.about_feedback_label)
val aboutFeedbackSub: String
    @Composable get() = stringResource(R.string.about_feedback_sub)
val aboutShareLabel: String
    @Composable get() = stringResource(R.string.about_share_label)
val aboutShareSub: String
    @Composable get() = stringResource(R.string.about_share_sub)
val aboutLegalEyebrow: String
    @Composable get() = stringResource(R.string.about_legal_eyebrow)
val aboutPrivacyLabel: String
    @Composable get() = stringResource(R.string.about_privacy_label)
val aboutTermsLabel: String
    @Composable get() = stringResource(R.string.about_terms_label)
val aboutFooterText: String
    @Composable get() = stringResource(R.string.about_footer_text)
