package com.sxt.chat.ui.material;

import android.app.Application;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.animation.LinearInterpolator;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.sxt.chat.R;
import com.sxt.chat.app.App;
import com.sxt.chat.app.ViewModelFactory;
import com.sxt.chat.databinding.FragmentMaterialBinding;
import com.sxt.chat.ui.adapter.MaterialAdapter;
import com.sxt.chat.ui.adapter.listener.EventListener;
import com.sxt.chat.ui.material.bottomAppbar.BottomAppbarActivity;
import com.sxt.chat.ui.material.bottomAppbar.BottomAppbarPositioningActivity;
import com.sxt.chat.ui.material.bottomAppbar.BottomAppbarSnackBarActivity;
import com.sxt.chat.ui.material.bottomNavogation.BottomNavigationActivity;
import com.sxt.chat.ui.material.bottomNavogation.BottomNavigationAnimatedActivity;
import com.sxt.chat.ui.material.bottomNavogation.BottomNavigationBadgeActivity;
import com.sxt.chat.ui.material.bottomNavogation.BottomNavigationTransitionsActivity;
import com.sxt.chat.ui.material.bottomSheet.BottomSheetActivity;
import com.sxt.chat.ui.material.cards.CardViewActivity;
import com.sxt.chat.ui.material.cards.CardViewDraggableActivity;
import com.sxt.chat.ui.material.cards.CardViewListDragActivity;
import com.sxt.chat.ui.material.cards.CardViewSelectableActivity;
import com.sxt.chat.ui.material.cards.CardViewSwipeActivity;
import com.sxt.chat.ui.material.chips.ChipsActivity;
import com.sxt.chat.ui.material.tablayout.CollapsingTabActivity;
import com.sxt.chat.ui.material.tablayout.TabLayoutActivity;
import com.sxt.chat.utils.SnackBarHelper;
import com.sxt.mvvm.data.material.Category;
import com.sxt.mvvm.view.ActivityCollector;
import com.sxt.mvvm.view.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.sxt.chat.utils.categroy.Categories.BottomAppBar;
import static com.sxt.chat.utils.categroy.Categories.BottomNavigation;
import static com.sxt.chat.utils.categroy.Categories.BottomSheet;
import static com.sxt.chat.utils.categroy.Categories.CardView;
import static com.sxt.chat.utils.categroy.Categories.Chips;
import static com.sxt.chat.utils.categroy.Categories.Tabs;
import static com.sxt.chat.utils.categroy.Categories.TopAppBar;

/**
 * Created by xt.sun
 * 2020/5/10
 * Material组件
 */
public class MaterialFragment extends BaseFragment<FragmentMaterialBinding, MaterialViewModel> {

    @Override
    public int getDisplayView() {
        return R.layout.fragment_material;
    }

    @NotNull
    @Override
    public MaterialViewModel initViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance((Application) App.getCtx());
        return createViewModel(this, factory, MaterialViewModel.class);
    }

    public void initView() {
        binding.toolbar.setNavigationOnClickListener(v -> {
            binding.drawerLayout.openDrawer(binding.navigationView, true);
        });
        binding.toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.favorite:
                    SnackBarHelper.showSnackBarShort(binding.coordinatorLayout, "favorite");
                    break;
                case R.id.search:
                    SnackBarHelper.showSnackBarShort(binding.coordinatorLayout, "Search");
                    break;
            }
            return false;
        });
    }

    @Override
    public void initObserver() {
        viewModel.categories.observe(this, this::refresh);
    }

    private void refresh(List<Category> categories) {
        MaterialAdapter adapter = new MaterialAdapter(getContext(), categories);
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this::onItemClick);
        adapter.setListener(getEventListener());
    }

    @NotNull
    private EventListener getEventListener() {
        return new EventListener() {
            @Override
            public void onExpand() {
                TransitionManager.beginDelayedTransition(binding.coordinatorLayout, getExpandTransition());
            }

            @Override
            public void onCollapse() {
                TransitionManager.beginDelayedTransition(binding.coordinatorLayout, getCollapseTransition());
            }
        };
    }

    @NotNull
    private AutoTransition getExpandTransition() {
        AutoTransition transition = new AutoTransition();
        transition.setDuration(200);
        transition.setInterpolator(new LinearInterpolator());
        return transition;
    }

    @NotNull
    private AutoTransition getCollapseTransition() {
        AutoTransition transition = new AutoTransition();
        transition.setDuration(180);
        transition.setInterpolator(new LinearInterpolator());
        return transition;
    }

    private void onItemClick(int position, Category category) {
        switch (category.getCategory()) {
            case TopAppBar:
                break;
            case BottomAppBar:
                bottomAppBar(position, category);
                break;
            case BottomNavigation:
                bottomNavigation(position, category);
                break;
            case BottomSheet:
                bottomSheet(position, category);
                break;
            case CardView:
                cardView(position, category);
                break;
            case Tabs:
                tabs(position, category);
                break;
            case Chips:
                chips(position, category);
                break;
        }
    }

    private void bottomSheet(int position, Category category) {
        switch (category.getTag().get(position)) {
            default:
                ActivityCollector.startActivity(getContext(), BottomSheetActivity.class);
                break;
        }
    }

    private void tabs(int position, Category category) {
        switch (category.getTag().get(position)) {
            default:
                ActivityCollector.startActivity(getContext(), CollapsingTabActivity.class);
                break;
        }
    }

    private void chips(int position, Category category) {
        switch (category.getTag().get(position)) {
            default:
                ActivityCollector.startActivity(getContext(), ChipsActivity.class);
                break;
        }
    }

    private void cardView(int position, Category category) {
        switch (category.getTag().get(position)) {
            case "Basic":
                ActivityCollector.startActivity(getContext(), CardViewActivity.class);
                break;
            case "Selectable":
                ActivityCollector.startActivity(getContext(), CardViewSelectableActivity.class);
                break;
            case "Draggable":
                ActivityCollector.startActivity(getContext(), CardViewDraggableActivity.class);
                break;
            case "List With Drag":
                ActivityCollector.startActivity(getContext(), CardViewListDragActivity.class);
                break;
            case "Swipe Dismiss":
                ActivityCollector.startActivity(getContext(), CardViewSwipeActivity.class);
                break;
            case "Collections":
                ActivityCollector.startActivity(getContext(), BottomNavigationTransitionsActivity.class);
                break;
            case "Scrolling":
                ActivityCollector.startActivity(getContext(), BottomNavigationTransitionsActivity.class);
                break;
        }
    }

    private void bottomNavigation(int position, Category category) {
        switch (category.getTag().get(position)) {
            case "Basic":
                ActivityCollector.startActivity(getContext(), BottomNavigationActivity.class);
                break;
            case "Badges":
                ActivityCollector.startActivity(getContext(), BottomNavigationBadgeActivity.class);
                break;
            case "Animated":
                ActivityCollector.startActivity(getContext(), BottomNavigationAnimatedActivity.class);
                break;
            case "Transitions":
                ActivityCollector.startActivity(getContext(), BottomNavigationTransitionsActivity.class);
                break;
        }
    }

    private void bottomAppBar(int position, Category category) {
        switch (category.getTag().get(position)) {
            case "Positioning":
                ActivityCollector.startActivity(getContext(), BottomAppbarPositioningActivity.class);
                break;
            case "Scrolling":
                ActivityCollector.startActivity(getContext(), BottomAppbarActivity.class);
                break;
            case "SnackBars":
                ActivityCollector.startActivity(getContext(), BottomAppbarSnackBarActivity.class);
                break;
        }
    }
}