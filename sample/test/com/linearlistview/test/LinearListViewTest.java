package com.linearlistview.test;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.linearlistview.LinearListView;
import com.linearlistview.LinearListView.OnItemClickListener;
import com.linearlistview.sample.MainActivity;
import com.linearlistview.sample.R;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class LinearListViewTest {

	private LinearListView mHorizontalListFromXmlWithAdapter;
	private LinearListView mVerticalListFromXmlWithEntries;
	private LinearListView mVerticalListFromXmlWithAdapter;
	private LinearListView mListFromJavaCode;

	private TestAdapter mAdapter;

	private class TestAdapter extends BaseAdapter {

		private String[] mEntries;
		private Context mContext;

		public TestAdapter(Context context) {
			mContext = context;
		}

		@Override
		public int getCount() {
			return mEntries == null ? 0 : mEntries.length;
		}

		@Override
		public Object getItem(int position) {
			return mEntries == null ? null : mEntries[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return new View(mContext);
		}

		public void setEntries(String[] entries) {
			mEntries = entries;
			notifyDataSetChanged();
		}
	};

	@Before
	public void setUp() {
		MainActivity activity = new MainActivity();
		activity.onCreate(null);
		mHorizontalListFromXmlWithAdapter = (LinearListView) activity
				.findViewById(R.id.horizontal_list);
		mVerticalListFromXmlWithEntries = (LinearListView) activity.findViewById(R.id.entries_list);
		mVerticalListFromXmlWithAdapter = (LinearListView) activity
				.findViewById(R.id.vertical_list);

		mListFromJavaCode = new LinearListView(activity);
		mAdapter = new TestAdapter(activity);

		mHorizontalListFromXmlWithAdapter.setAdapter(mAdapter);
		mVerticalListFromXmlWithAdapter.setAdapter(mAdapter);
		mListFromJavaCode.setAdapter(mAdapter);
	}

	@Test
	public void testListSizeShouldBeEqualToAdapterSize() {
		int expected = new Activity().getResources().getStringArray(R.array.countries).length;
		// assertThat(mVerticalListFromXmlWithEntries.getChildCount()).isEqualTo(expected);

		testListSizeShouldBeEqualToAdapterSize_WithSize(6);
		testListSizeShouldBeEqualToAdapterSize_WithSize(0);
		testListSizeShouldBeEqualToAdapterSize_WithSize(3);
	}

	private void testListSizeShouldBeEqualToAdapterSize_WithSize(int size) {
		mAdapter.setEntries(new String[size]);
		assertThat(mHorizontalListFromXmlWithAdapter.getChildCount()).isEqualTo(size);
		assertThat(mVerticalListFromXmlWithAdapter.getChildCount()).isEqualTo(size);
		assertThat(mListFromJavaCode.getChildCount()).isEqualTo(size);
	}

	@Test
	public void testListShouldDisplayEmptyViewWhenEmpty() {
		View emptyView = new View(new Activity());
		// mVerticalListFromXmlWithEntries.setEmptyView(emptyView);
		// assertThat(mVerticalListFromXmlWithEntries.getVisibility()).isEqualTo(View.VISIBLE);
		// assertThat(mVerticalListFromXmlWithEntries.getEmptyView().getVisibility()).isEqualTo(View.GONE);

		testListShouldDisplayEmptyViewWhenEmpty_WithSize(1, false);
		testListShouldDisplayEmptyViewWhenEmpty_WithSize(0, false);

		mHorizontalListFromXmlWithAdapter.setEmptyView(emptyView);
		mVerticalListFromXmlWithAdapter.setEmptyView(emptyView);
		mListFromJavaCode.setEmptyView(emptyView);

		testListShouldDisplayEmptyViewWhenEmpty_WithSize(1, true);
		testListShouldDisplayEmptyViewWhenEmpty_WithSize(0, true);
	}

	private void testListShouldDisplayEmptyViewWhenEmpty_WithSize(int size, boolean isEmptyViewSet) {
		int listExpected;
		int emptyViewExpected;
		if (size == 0 && isEmptyViewSet) {
			listExpected = View.GONE;
			emptyViewExpected = View.VISIBLE;
		} else {
			listExpected = View.VISIBLE;
			emptyViewExpected = View.GONE;
		}

		mAdapter.setEntries(new String[size]);

		assertThat(mHorizontalListFromXmlWithAdapter.getVisibility()).isEqualTo(listExpected);
		assertThat(mVerticalListFromXmlWithAdapter.getVisibility()).isEqualTo(listExpected);
		assertThat(mListFromJavaCode.getVisibility()).isEqualTo(listExpected);

		if (isEmptyViewSet) {
			assertThat(mHorizontalListFromXmlWithAdapter.getEmptyView().getVisibility()).isEqualTo(
					emptyViewExpected);
			assertThat(mVerticalListFromXmlWithAdapter.getEmptyView().getVisibility()).isEqualTo(
					emptyViewExpected);
			assertThat(mListFromJavaCode.getEmptyView().getVisibility()).isEqualTo(
					emptyViewExpected);
		}
	}

	@Test
	public void testClickingEnabledListItemsShouldInvokeListener() {
//		mVerticalListFromXmlWithEntries.setOnItemClickListener(new TestOnItemClickListener());
//		assertThat(mVerticalListFromXmlWithEntries)
		
		mHorizontalListFromXmlWithAdapter.setOnItemClickListener(new TestOnItemClickListener());
		mVerticalListFromXmlWithAdapter.setOnItemClickListener(new TestOnItemClickListener());
		mListFromJavaCode.setOnItemClickListener(new TestOnItemClickListener());
		
		testClickingEnabledListItemsShouldInvokeListener_WithSizeAndIndex(1,0);
		testClickingEnabledListItemsShouldInvokeListener_WithSizeAndIndex(7,2);
		testClickingEnabledListItemsShouldInvokeListener_WithSizeAndIndex(10,3);
		testClickingEnabledListItemsShouldInvokeListener_WithSizeAndIndex(200,199);
		
	}

	private void testClickingEnabledListItemsShouldInvokeListener_WithSizeAndIndex(int size,
			int index) {
		
		mAdapter.setEntries(new String[size]);
		
		testOnItemCickListenerValuesAreSet(mHorizontalListFromXmlWithAdapter, index);
		testOnItemCickListenerValuesAreSet(mVerticalListFromXmlWithAdapter, index);
		testOnItemCickListenerValuesAreSet(mListFromJavaCode, index);

	}
	
	private void testOnItemCickListenerValuesAreSet(LinearListView list, int index) {
		list.getChildAt(index).performClick();
		TestOnItemClickListener listener = (TestOnItemClickListener) list.getOnItemClickListener();
		assertThat(listener.mId).isEqualTo(mAdapter.getItemId(index));
		assertThat(listener.mLinearListView).isEqualTo(list);
		assertThat(listener.mPosition).isEqualTo(index);
		assertThat(listener.mView).isEqualTo(list.getChildAt(index));		
	}
		
	private class TestOnItemClickListener implements OnItemClickListener {
		
		public LinearListView mLinearListView;
		public View mView;
		public int mPosition;
		public long mId;

		@Override
		public void onItemClick(LinearListView arg0, View arg1, int arg2, long arg3) {
			mLinearListView = arg0;
			mView = arg1;
			mPosition = arg2;
			mId = arg3;		
		}
		
	}

}
