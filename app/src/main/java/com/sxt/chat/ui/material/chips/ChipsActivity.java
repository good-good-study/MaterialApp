package com.sxt.chat.ui.material.chips;

import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.sxt.chat.R;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.data.ChipData;
import com.sxt.chat.databinding.ActivityChipsBinding;
import com.sxt.chat.ui.material.collapsing.CollapsingToolbarActivity;
import com.sxt.chat.utils.SnackBarHelper;
import com.sxt.mvvm.view.ActivityCollector;
import com.sxt.mvvm.view.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/10
 * Chips
 */
public class ChipsActivity extends BaseActivity<ActivityChipsBinding, ChipsViewModel> {
    @Override
    public int getDisplayView() {
        return R.layout.activity_chips;
    }

    @NotNull
    @Override
    public ChipsViewModel initViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        return createViewModel(this, factory, ChipsViewModel.class);
    }

    @Override
    public void initView() {
        setToolbar();
    }

    @Override
    public void initObserver() {
        viewModel.chips.observe(this, this::bindChips);
        viewModel.chipsAction.observe(this, this::bindChipsFilter);
        viewModel.chipsEntry.observe(this, this::bindChipsEntry);
        buildChipsAction();
    }

    private void setToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    //Choice
    private void bindChips(List<ChipData> chips) {
        ChipGroup choice = binding.chipsGroupChoice.chipGroup;
        for (ChipData data : chips) {
            Chip chip = getChoiceChip();
            chip.setText(data.getTitle());
            choice.addView(chip);
            //设置chip点击事件
            chip.setOnCheckedChangeListener(buildChipChoiceListener(data));
        }
    }

    private CompoundButton.OnCheckedChangeListener buildChipChoiceListener(ChipData data) {
        return (v, isChecked) -> {
            Chip chip = (Chip) v;
            if (isChecked) {
                chip.setChipBackgroundColorResource(data.getColor());
            } else {
                chip.setChipBackgroundColorResource(R.color.chipGreyColor);
            }
        };
    }

    //Filter
    private void bindChipsFilter(List<ChipData> chips) {
        ChipGroup filter = binding.chipsGroupFilter.chipGroup;
        for (ChipData data : chips) {
            Chip chip = getFilterChip();
            chip.setText(data.getTitle());
            filter.addView(chip);
            //设置chip点击事件
            chip.setOnCheckedChangeListener(buildChipFilterListener(data));
        }
    }

    private CompoundButton.OnCheckedChangeListener buildChipFilterListener(ChipData data) {
        return (view, isChecked) -> {
            Chip chip = (Chip) view;
            if (isChecked) {
                chip.setChipBackgroundColorResource(data.getColor());
            } else {
                chip.setChipBackgroundColorResource(R.color.chipGreyColor);
            }
            if (!isChecked) return;
            SnackBarHelper.showSnackBarShort(binding.coordinatorLayout, data.getTitle(), v -> {
            });
        };
    }

    //Action
    private void buildChipsAction() {
        ChipGroup action = binding.chipsGroupAction.chipGroup;
        action.setOnCheckedChangeListener(buildChipActionListener());
    }

    @NotNull
    private ChipGroup.OnCheckedChangeListener buildChipActionListener() {
        return (group, checkedId) -> {
            Chip chip = group.findViewById(checkedId);
            if (chip == null) return;
            if (TextUtils.isEmpty(chip.getText())) return;
            SnackBarHelper.showSnackBarShort(binding.coordinatorLayout, chip.getText(), v -> {
            });
        };
    }

    //Entry
    private void bindChipsEntry(List<ChipData> chips) {
        ChipGroup entry = binding.chipsGroupEntry.chipGroup;
        for (ChipData data : chips) {
            Chip chip = getEntryChip();
            chip.setText(data.getTitle());
            chip.setChipIconResource(data.getIcon());

            entry.addView(chip);
            //设置chip点击事件
            chip.setOnCheckedChangeListener(buildChipEntryListener(data));
            chip.setOnCloseIconClickListener(buildChipCloseListener(chip));
        }
    }

    private CompoundButton.OnCheckedChangeListener buildChipEntryListener(ChipData data) {
        return (v, isChecked) -> {
            Chip chip = (Chip) v;
            chip.setCloseIconVisible(isChecked);
            chip.setChipIconVisible(!isChecked);
            if (isChecked) {
                chip.setChipBackgroundColorResource(data.getColor());
            } else {
                chip.setChipBackgroundColorResource(R.color.chipGreyColor);
            }
        };
    }

    @NotNull
    private View.OnClickListener buildChipCloseListener(Chip chip) {
        return v -> chip.setChecked(false);
    }

    private Chip getEntryChip() {
        return (Chip) getLayoutInflater().inflate(R.layout.item_chip_entry, null);
    }

    private Chip getFilterChip() {
        return (Chip) getLayoutInflater().inflate(R.layout.item_chip_filter, null);
    }

    private Chip getChoiceChip() {
        return (Chip) getLayoutInflater().inflate(R.layout.item_chip_choice, null);
    }
}