package com.moviebomber.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.moviebomber.R;
import com.moviebomber.adapter.MoviePagerAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoviePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviePageFragment extends Fragment implements MaterialTabListener, View.OnClickListener {

	public static final int[] TABS = {R.string.tab_movie_thisweek,
			R.string.tab_movie_intheater, R.string.tab_movie_comingsoon};

	public enum SortBy {
		LASTEST,
		OLDEST,
		BOMBER
	}

	@InjectView(R.id.tab_movie_list)
	MaterialTabHost mTabMoviewList;
	@InjectView(R.id.pager_movie)
	ViewPager mPagerMovie;
	@InjectView(R.id.fab_sort_lastest)
	FloatingActionButton mFabLastest;
	@InjectView(R.id.fab_sort_oldest)
	FloatingActionButton mFabOldest;
	@InjectView(R.id.fab_sort_bomber)
	FloatingActionButton mFabBomber;
	@InjectView(R.id.fab_menu_sort)
	FloatingActionsMenu mFabMenuSort;
	@InjectView(R.id.fab_overlay)
	View mFabOverlay;

	private MoviePagerAdapter mAdapter;
	private int mCurrentTab = 0;

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @return A new instance of fragment MovieListFragment.
	 */
	public static MoviePageFragment newInstance() {
		MoviePageFragment fragment = new MoviePageFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public MoviePageFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_movie_page, container, false);
		this.initView(rootView);
		return rootView;
	}

	private void initView(View rootView) {
		ButterKnife.inject(this, rootView);
		mAdapter = new MoviePagerAdapter(getFragmentManager(), getActivity());
		this.mPagerMovie.setAdapter(mAdapter);
		this.mPagerMovie.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				mTabMoviewList.setSelectedNavigationItem(position);
//				MovieListFragment fragment = (MovieListFragment) mAdapter.instantiateItem(mPagerMovie, position);
//				fragment.loadMovieList(true);
				mCurrentTab = position;
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		for (int i = 0; i < mAdapter.getCount(); i++) {
			this.mTabMoviewList.addTab(this.mTabMoviewList.newTab()
					.setTabListener(this)
					.setText(getActivity().getResources().getString(TABS[i])));
		}
		this.mFabMenuSort.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
			@Override
			public void onMenuExpanded() {
				mFabOverlay.setVisibility(View.VISIBLE);
			}

			@Override
			public void onMenuCollapsed() {
				mFabOverlay.setVisibility(View.GONE);
			}
		});
		this.mFabLastest.setOnClickListener(this);
		this.mFabOldest.setOnClickListener(this);
		this.mFabBomber.setOnClickListener(this);
		this.mFabOverlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mFabMenuSort.collapse();
				mFabOverlay.setVisibility(View.GONE);
			}
		});
	}



	@Override
	public void onClick(View v) {
		MovieListFragment fragment = (MovieListFragment)mAdapter.instantiateItem(mPagerMovie,
				this.mCurrentTab);
		switch (v.getId()) {
			case R.id.fab_sort_lastest:
				fragment.setSortBy(SortBy.LASTEST);
				break;
			case R.id.fab_sort_oldest:
				fragment.setSortBy(SortBy.OLDEST);
				break;
			case R.id.fab_sort_bomber:
				fragment.setSortBy(SortBy.BOMBER);
				break;
		}
		mFabMenuSort.collapse();
		fragment.loadMovieList(true);
	}

	@Override
	public void onTabSelected(MaterialTab materialTab) {
		int position = materialTab.getPosition();
		this.mPagerMovie.setCurrentItem(position);
		this.mCurrentTab = position;
	}

	@Override
	public void onTabReselected(MaterialTab materialTab) {

	}

	@Override
	public void onTabUnselected(MaterialTab materialTab) {
	}
}
