/**
 * Copyright (C) 2014-2019 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.ubl23;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.math.BigDecimal;

import org.junit.Test;

import com.helger.commons.state.ESuccess;
import com.helger.datetime.util.PDTXMLConverter;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.CustomerPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.InvoiceLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.ItemType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.MonetaryTotalType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.SupplierPartyType;
import oasis.names.specification.ubl.schema.xsd.invoice_23.InvoiceType;

/**
 * Create a minimal UBL 2.3 invoice from scratch.
 *
 * @author Philip Helger
 */
public final class CreateInvoiceFromScratchFuncTest
{
  @Test
  public void testCreateInvoiceFromScratch ()
  {
    final String sCurrency = "EUR";

    final InvoiceType aInvoice = new InvoiceType ();
    aInvoice.setID ("Dummy Invoice number");
    aInvoice.setIssueDate (PDTXMLConverter.getXMLCalendarDateNow ());

    final SupplierPartyType aSupplier = new SupplierPartyType ();
    aInvoice.setAccountingSupplierParty (aSupplier);

    final CustomerPartyType aCustomer = new CustomerPartyType ();
    aInvoice.setAccountingCustomerParty (aCustomer);

    final MonetaryTotalType aMT = new MonetaryTotalType ();
    aMT.setPayableAmount (BigDecimal.TEN).setCurrencyID (sCurrency);
    aInvoice.setLegalMonetaryTotal (aMT);

    final InvoiceLineType aLine = new InvoiceLineType ();
    aLine.setID ("1");

    final ItemType aItem = new ItemType ();
    aLine.setItem (aItem);

    aLine.setLineExtensionAmount (BigDecimal.TEN).setCurrencyID (sCurrency);

    aInvoice.addInvoiceLine (aLine);

    final ESuccess eSuccess = UBL23Writer.invoice ().write (aInvoice, new File ("target/dummy-invoice.xml"));
    assertTrue (eSuccess.isSuccess ());
  }
}