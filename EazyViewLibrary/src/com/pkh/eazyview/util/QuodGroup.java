package com.pkh.eazyview.util;

import java.util.ArrayList;

import com.pkh.eazyview.util.EazyViewUtil.QUODRANT;

public class QuodGroup {

	ArrayList<ViewHolderDetail> holderDetails=null;
	QUODRANT quodrantName;
	/**
	 * 
	 * @return : list of ViewHolder information
	 */
	public ArrayList<ViewHolderDetail> getHolderDetails() {
		return holderDetails;
	}
	public void setHolderDetails(ArrayList<ViewHolderDetail> holderDetails) {
		this.holderDetails = holderDetails;
	}
	public QUODRANT getQuodrantName() {
		return quodrantName;
	}
	public void setQuodrantName(QUODRANT quodrantName) {
		this.quodrantName = quodrantName;
	}
		
}
